package com.work.taskmanager.service;

import com.work.taskmanager.model.Project;
import com.work.taskmanager.model.Task;
import com.work.taskmanager.model.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {

    Project save(Project project);
    Project create(ProjectDTO projectDTO);
    Project findById(Long projectId);
    List<Project> findAll();
    List<Project> findByUserId(Long userId);
    Project delete(Project project);
    void deleteById(Long projectId);

}
