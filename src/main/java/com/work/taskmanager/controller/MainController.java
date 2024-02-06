package com.work.taskmanager.controller;

import com.work.taskmanager.service.impl.ProjectServiceImpl;
import com.work.taskmanager.service.impl.TaskServiceImpl;
import com.work.taskmanager.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final UserServiceImpl userService;
    private final ProjectServiceImpl projectService;
    private final TaskServiceImpl taskService;

    @Autowired
    public MainController(ProjectServiceImpl projectService, UserServiceImpl userService,
                          TaskServiceImpl taskService) {
        this.projectService = projectService;
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping
    public String hello(Model model, Authentication authentication) {
        if (authentication != null) {
            model.addAttribute("projects", projectService.findByUserId(
                userService.findByUsername(authentication.getName()).getUserId()
            ));
        }
        return "index";
    }

}
