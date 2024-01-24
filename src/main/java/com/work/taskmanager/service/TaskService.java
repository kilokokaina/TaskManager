package com.work.taskmanager.service;

import com.work.taskmanager.model.Task;
import com.work.taskmanager.model.User;
import com.work.taskmanager.model.dto.TaskDTO;

import java.util.List;

public interface TaskService {

    Task save(Task task);
    Task create(TaskDTO taskDTO, User user);
    Task findById(Long taskId);
    Task findByTitle(String title);
    List<Task> findAllByAuthor(String username);
    void delete(Task task);
    void deleteById(Long taskId);

}
