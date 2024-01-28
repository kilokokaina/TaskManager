package com.work.taskmanager.api;

import com.work.taskmanager.model.Project;
import com.work.taskmanager.model.dto.ProjectDTO;
import com.work.taskmanager.service.impl.ProjectServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/project")
public class ProjectAPI {

    private final ProjectServiceImpl projectService;

    @Autowired
    public ProjectAPI(ProjectServiceImpl projectRepository) {
        this.projectService = projectRepository;
    }

    @GetMapping
    public ResponseEntity<List<Project>> findAll() {
        return ResponseEntity.ok(projectService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Project> findById(@PathVariable(value = "id") Project project) {
        log.info(project.getTaskSet().toString());
        return ResponseEntity.ok(project);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Project>> findByUserId(@PathVariable(value = "id") Long userId) {
        return ResponseEntity.ok(projectService.findByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<Project> addProject(@RequestBody ProjectDTO projectDTO) {
        return ResponseEntity.ok(projectService.create(projectDTO));
    }

}
