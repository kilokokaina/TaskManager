package com.work.taskmanager.service.impl;

import com.work.taskmanager.model.Task;
import com.work.taskmanager.model.TaskStatus;
import com.work.taskmanager.model.User;
import com.work.taskmanager.model.dto.TaskDTO;
import com.work.taskmanager.repository.TaskRepository;
import com.work.taskmanager.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task create(TaskDTO taskDTO, User user) {
        Task task = new Task();

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(TaskStatus.ASSIGNED.name());
        task.setProjectId(taskDTO.getProjectId());
        task.setAuthor(user.getUsername());

        return save(task);
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
        return taskRepository.findAllByAuthor(username);
    }

    public List<Task> findByProject(Long projectId) {
        return taskRepository.findAllByProjectId(projectId);
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
