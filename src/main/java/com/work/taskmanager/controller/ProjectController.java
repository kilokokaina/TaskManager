package com.work.taskmanager.controller;

import com.work.taskmanager.model.Project;
import com.work.taskmanager.model.Task;
import com.work.taskmanager.repository.ProjectRepository;
import com.work.taskmanager.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("project")
public class ProjectController {

    private final UserServiceImpl userService;
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectController(ProjectRepository projectRepository, UserServiceImpl userService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    @GetMapping("{id}")
    public String project(@PathVariable(value = "id") Project project, Model model, Authentication authentication) {
        Set<Task> taskSet = project.getTaskSet().stream().filter(task ->
            task.getTargetUser().contains(userService.findByUsername(authentication.getName()))
        ).collect(Collectors.toSet());

        model.addAttribute("projects", projectRepository.findByUserId(
                userService.findByUsername(authentication.getName()).getUserId())
        );

        model.addAttribute("project", project);
        model.addAttribute("taskSet", taskSet);

        return "project";
    }

}
