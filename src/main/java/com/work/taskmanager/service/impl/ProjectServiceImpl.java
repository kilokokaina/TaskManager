package com.work.taskmanager.service.impl;

import com.work.taskmanager.model.Project;
import com.work.taskmanager.model.User;
import com.work.taskmanager.model.dto.ProjectDTO;
import com.work.taskmanager.repository.ProjectRepository;
import com.work.taskmanager.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserServiceImpl userService;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, UserServiceImpl userService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    @Override
    public Project findById(Long projectId) {
        return projectRepository.findById(projectId).orElse(null);
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project create(ProjectDTO projectDTO) {
        Project project = new Project();

        project.setTitle(projectDTO.getName());
        List<User> userSet = new ArrayList<>();
        for (String username : projectDTO.getUserSet()) {
            userSet.add(userService.findByUsername(username));
        }
        project.setUserList(userSet);

        return this.save(project);
    }

    @Override
    public List<Project> findByUserId(Long userId) {
        return projectRepository.findByUserId(userId);
    }

    @Override
    public Project delete(Project project) {
        projectRepository.delete(project);
        return project;
    }

    @Override
    public void deleteById(Long projectId) {
        projectRepository.deleteById(projectId);
    }
}
