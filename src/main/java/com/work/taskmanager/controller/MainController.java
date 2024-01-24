package com.work.taskmanager.controller;

import com.work.taskmanager.repository.ProjectRepository;
import com.work.taskmanager.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final UserServiceImpl userService;
    private final ProjectRepository projectRepository;

    @Autowired
    public MainController(ProjectRepository projectRepository, UserServiceImpl userService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    @GetMapping
    public String hello(Model model, Authentication authentication) {
        if (authentication != null) {
            model.addAttribute("projects", projectRepository.findByUserId(
                    userService.findByUsername(authentication.getName()).getUserId())
            );
        }
        return "index";
    }

}
