package com.work.taskmanager.api;

import com.work.taskmanager.model.Task;
import com.work.taskmanager.model.User;
import com.work.taskmanager.model.dto.TaskDTO;
import com.work.taskmanager.service.impl.TaskServiceImpl;
import com.work.taskmanager.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/task")
public class TaskAPI {

    private final TaskServiceImpl taskService;
    private final UserServiceImpl userService;

    @Autowired
    public TaskAPI(TaskServiceImpl taskService, UserServiceImpl userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Task> findTask(@PathVariable(value = "id") Task task) {
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody TaskDTO taskDTO, Authentication authentication) {
        return ResponseEntity.ok(taskService.create(taskDTO, userService.findByUsername(authentication.getName())));
    }

    @GetMapping("test/{id}")
    public ResponseEntity<List<Task>> findTaskByAuthor(@PathVariable(value = "id") User user) {
        return ResponseEntity.ok(taskService.findAllByAuthor(user.getUsername()));
    }

}
