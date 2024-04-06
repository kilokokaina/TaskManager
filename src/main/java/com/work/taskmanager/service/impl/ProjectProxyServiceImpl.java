package com.work.taskmanager.service.impl;

import com.work.taskmanager.model.Project;
import com.work.taskmanager.model.Task;
import com.work.taskmanager.model.User;
import com.work.taskmanager.model.proxy.ProjectProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ProjectProxyServiceImpl {

    private final TaskServiceImpl taskService;

    @Autowired
    public ProjectProxyServiceImpl(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    public ProjectProxy getProjectModel(Project project, User user) {
        ProjectProxy projectProxy = new ProjectProxy();

        List<Task> taskList = taskService.findByProject(project.getProjectId())
                .stream().sorted(Comparator.comparing(Task::getCreationDate).reversed())
                .toList();
        List<Task> userTaskList = user.getTaskList().stream()
                .filter(task -> task.getProjectId().equals(project.getProjectId()))
                .sorted(Comparator.comparing(Task::getCreationDate).reversed())
                .toList();

        projectProxy.setProjectTaskList(taskList);
        projectProxy.setUserTaskList(userTaskList);
        projectProxy.setProject(project);

        return projectProxy;
    }

}
