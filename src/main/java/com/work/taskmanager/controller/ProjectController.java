package com.work.taskmanager.controller;

import com.work.taskmanager.model.Project;
import com.work.taskmanager.model.Task;
import com.work.taskmanager.model.User;
import com.work.taskmanager.service.impl.ProjectServiceImpl;
import com.work.taskmanager.service.impl.TaskServiceImpl;
import com.work.taskmanager.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("project")
public class ProjectController {

    private final UserServiceImpl userService;
    private final ProjectServiceImpl projectService;
    private final TaskServiceImpl taskService;

    @Autowired
    public ProjectController(ProjectServiceImpl projectService, UserServiceImpl userService,
                             TaskServiceImpl taskService) {
        this.projectService = projectService;
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("{id}")
    public String project(@PathVariable(value = "id") Project project, Model model, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());

        List<Task> taskList = user.getTaskList().stream().filter(task ->
                task.getProjectId().equals(project.getProjectId())
        ).sorted(Comparator.comparing(Task::getCreationDate).reversed()).collect(Collectors.toList());

        model.addAttribute("projects", projectService.findByUserId(
                userService.findByUsername(authentication.getName()).getUserId())
        );

        Set<String> adminUsernameSet = project.getAdminList().stream().map(User::getUsername).collect(Collectors.toSet());
        Set<String> curatorUsernameSet = project.getCuratorList().stream().map(User::getUsername).collect(Collectors.toSet());

        boolean isAdmin = adminUsernameSet.contains(user.getUsername());
        boolean isCurator = curatorUsernameSet.contains(user.getUsername());

        model.addAttribute("projectTaskSet",
                taskService.findByProject(project.getProjectId())
        );
        model.addAttribute("userTaskSet", taskList);
        model.addAttribute("curator", isCurator);
        model.addAttribute("project", project);
        model.addAttribute("admin", isAdmin);

        return "project";
    }

}
