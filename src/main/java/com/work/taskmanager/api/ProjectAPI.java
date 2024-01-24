package com.work.taskmanager.api;

import com.work.taskmanager.model.Project;
import com.work.taskmanager.model.User;
import com.work.taskmanager.model.dto.ProjectDTO;
import com.work.taskmanager.repository.ProjectRepository;
import com.work.taskmanager.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/project")
public class ProjectAPI {

    private final ProjectRepository projectRepository;
    private final UserServiceImpl userService;

    @Autowired
    public ProjectAPI(ProjectRepository projectRepository, UserServiceImpl userService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Project>> findAll() {
        return ResponseEntity.ok(projectRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Project> findById(@PathVariable(value = "id") Project project) {
        return ResponseEntity.ok(project);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Project>> findByUserId(@PathVariable(value = "id") Long userId) {
        return ResponseEntity.ok(projectRepository.findByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<Project> addProject(@RequestBody ProjectDTO projectDTO) {
        Project newProject = new Project();

        newProject.setName(projectDTO.getName());

        Set<User> userSet = new HashSet<>();
        for (String username : projectDTO.getUserSet()) {
            userSet.add(userService.findByUsername(username));
        }
        newProject.setUserSet(userSet);

        return ResponseEntity.ok(projectRepository.save(newProject));
    }

}
