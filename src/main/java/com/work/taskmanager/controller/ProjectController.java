package com.work.taskmanager.controller;

import com.work.taskmanager.model.Project;
import com.work.taskmanager.model.User;
import com.work.taskmanager.service.impl.ProjectProxyServiceImpl;
import com.work.taskmanager.service.impl.ProjectServiceImpl;
import com.work.taskmanager.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("project")
public class ProjectController {

    private final ProjectProxyServiceImpl projectProxyService;
    private final ProjectServiceImpl projectService;
    private final UserServiceImpl userService;

    @Autowired
    public ProjectController(UserServiceImpl userService, ProjectServiceImpl projectService,
                             ProjectProxyServiceImpl projectProxyService) {
        this.projectProxyService = projectProxyService;
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping("{id}")
    public String project(@PathVariable(value = "id") Project project, Model model, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());

        model.addAttribute("project", projectProxyService.getProjectModel(project, user));
        model.addAttribute("projects", projectService.findByUserId(user.getUserId()));

        Set<String> adminUsernameSet = project.getAdminList().stream().map(User::getUsername).collect(Collectors.toSet());
        Set<String> curatorUsernameSet = project.getCuratorList().stream().map(User::getUsername).collect(Collectors.toSet());

        boolean isAdmin = adminUsernameSet.contains(user.getUsername());
        boolean isCurator = curatorUsernameSet.contains(user.getUsername());

        model.addAttribute("curator", isCurator);
        model.addAttribute("admin", isAdmin);

        return "v3/project";
    }

    @GetMapping("v2/{id}")
    public String projectV2(@PathVariable(value = "id") Project project, Model model, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());

        model.addAttribute("project", projectProxyService.getProjectModel(project, user));
        model.addAttribute("projects", projectService.findByUserId(user.getUserId()));

        Set<String> adminUsernameSet = project.getAdminList().stream().map(User::getUsername).collect(Collectors.toSet());
        Set<String> curatorUsernameSet = project.getCuratorList().stream().map(User::getUsername).collect(Collectors.toSet());

        boolean isAdmin = adminUsernameSet.contains(user.getUsername());
        boolean isCurator = curatorUsernameSet.contains(user.getUsername());

        model.addAttribute("curator", isCurator);
        model.addAttribute("admin", isAdmin);

        return "v2/project";
    }

}
