package com.work.taskmanager.service.impl;

import com.work.taskmanager.model.Task;
import com.work.taskmanager.model.User;
import com.work.taskmanager.model.dto.TaskDTO;
import com.work.taskmanager.repository.TaskRepository;
import com.work.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserServiceImpl userService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserServiceImpl userService) {
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

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setAuthor(user);

        return save(task);
    }

    @Override
    public Task findById(Long taskId) {
        return taskRepository.findById(taskId).isPresent() ? taskRepository.findById(taskId).get() : null;
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
