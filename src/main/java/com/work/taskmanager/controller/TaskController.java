package com.work.taskmanager.controller;

import com.work.taskmanager.model.Task;
import com.work.taskmanager.service.impl.ProjectServiceImpl;
import com.work.taskmanager.service.impl.TaskServiceImpl;
import com.work.taskmanager.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("task")
public class TaskController {

    public final ProjectServiceImpl projectService;
    public final TaskServiceImpl taskService;
    public final UserServiceImpl userService;

    @Autowired
    public TaskController(ProjectServiceImpl projectService, TaskServiceImpl taskService,
                          UserServiceImpl userService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping
    public String task(@RequestParam(value = "is_author") boolean isAuthor, Model model,
                       Authentication authentication) {
        List<Task> taskList = new ArrayList<>();
        String username = authentication.getName();

        if (!isAuthor) taskList.addAll(userService.findByUsername(username).getTaskList());
        else taskList.addAll(taskService.findAllByAuthor(username));

        model.addAttribute("projects", projectService.findByUserId(
                userService.findByUsername(authentication.getName()).getUserId()
        ));
        model.addAttribute("tasks", taskList);

        return "task";
    }

}
