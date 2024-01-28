package com.work.taskmanager.service.impl;

import com.work.taskmanager.model.Project;
import com.work.taskmanager.model.Task;
import com.work.taskmanager.model.User;
import com.work.taskmanager.model.dto.TaskDTO;
import com.work.taskmanager.repository.ProjectRepository;
import com.work.taskmanager.repository.TaskRepository;
import com.work.taskmanager.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final UserServiceImpl userService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserServiceImpl userService,
                           ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task create(TaskDTO taskDTO, User user) {
        Task task = new Task();

        Set<User> userSet = new HashSet<>();
        Arrays.stream(taskDTO.getTargetUser()).forEach(userId ->
                userSet.add(userService.findById(userId))
        );

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setTargetUser(userSet);
        task.setAuthor(user);
        save(task);

        Project project = projectRepository.findById(taskDTO.getProjectId()).orElse(null);

        Assert.notNull(project, "Project is empty");
        Set<Task> taskSet = new HashSet<>(project.getTaskSet());
        project.getTaskSet().clear();
        projectRepository.save(project);

        taskSet.add(task);
        project.setTaskSet(taskSet);
        projectRepository.save(project);

        return task;
    }

    @Override
    public Task findById(Long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

    @Override
    public Task findByTitle(String title) {
        return taskRepository.findByTitle(title);
    }

    @Override
    public List<Task> findAllByAuthor(String username) {
        return taskRepository.findAllByAuthor(userService.findByUsername(username));
    }

    @Override
    public void delete(Task task) {
        taskRepository.delete(task);
    }

    @Override
    public void deleteById(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}
