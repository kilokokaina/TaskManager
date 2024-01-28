package com.work.taskmanager;

import com.work.taskmanager.model.Department;
import com.work.taskmanager.model.Organization;
import com.work.taskmanager.model.Task;
import com.work.taskmanager.model.User;
import com.work.taskmanager.repository.ProjectRepository;
import com.work.taskmanager.service.impl.DepServiceImpl;
import com.work.taskmanager.service.impl.OrgServiceImpl;
import com.work.taskmanager.service.impl.TaskServiceImpl;
import com.work.taskmanager.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.*;

@Slf4j
@SpringBootApplication
public class TaskManagerApplication {

    private final ProjectRepository projectRepository;
    private final UserServiceImpl userService;
    private final TaskServiceImpl taskService;

    @Autowired
    public TaskManagerApplication(UserServiceImpl userService, TaskServiceImpl taskService,
                                  ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
        this.userService = userService;
        this.taskService = taskService;
    }

    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
    }

    @Bean
    public CommandLineRunner cmd() {
        return args -> {
            Task task = taskService.findById(3L);
            Set<User> userSet = task.getTargetUser();
            userSet.add(userService.findById(5L));
            task.setTargetUser(userSet);
//            taskService.save(task);
        };
    }

}
