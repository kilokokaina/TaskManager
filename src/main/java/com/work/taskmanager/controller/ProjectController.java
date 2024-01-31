package com.work.taskmanager.controller;

import com.work.taskmanager.model.Project;
import com.work.taskmanager.model.Task;
import com.work.taskmanager.model.User;
import com.work.taskmanager.repository.ProjectRepository;
import com.work.taskmanager.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
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
        User user = userService.findByUsername(authentication.getName());
        Set<Task> taskSet = user.getTaskList().stream().filter(task ->
            task.getProjectId().equals(project.getProjectId())
        ).collect(Collectors.toSet());

        model.addAttribute("projects", projectRepository.findByUserId(
                userService.findByUsername(authentication.getName()).getUserId())
        );

        Set<String> adminUsernameSet = project.getAdminList().stream().map(User::getUsername).collect(Collectors.toSet());
        Set<String> curatorUsernameSet = project.getCuratorList().stream().map(User::getUsername).collect(Collectors.toSet());

        boolean isAdmin = adminUsernameSet.contains(user.getUsername());
        boolean isCurator = curatorUsernameSet.contains(user.getUsername());

        model.addAttribute("project", project);
        model.addAttribute("taskSet", taskSet);
        model.addAttribute("admin", isAdmin);
        model.addAttribute("curator", isCurator);

        return "project";
    }

}
