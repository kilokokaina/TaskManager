package com.work.taskmanager.api;

import com.work.taskmanager.model.Department;
import com.work.taskmanager.model.Organization;
import com.work.taskmanager.model.User;
import com.work.taskmanager.model.dto.DepDTO;
import com.work.taskmanager.service.impl.DepServiceImpl;
import com.work.taskmanager.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/dep")
public class DepAPI {

    private final DepServiceImpl depService;

    @Autowired
    public DepAPI(DepServiceImpl depService) {
        this.depService = depService;
    }

    @GetMapping("{id}")
    public ResponseEntity<List<Department>> findAllByOrg(@PathVariable(value = "id") Organization organization) {
        return ResponseEntity.ok(depService.findAllByOrg(organization));
    }

    @PostMapping("{id}")
    public ResponseEntity<Department> addToOrg(
            @PathVariable(value = "id") Organization organization, @RequestBody DepDTO depDTO) {
        return ResponseEntity.ok(depService.create(depDTO, organization));
    }

    @GetMapping("{org_id}/{dep_id}")
    public ResponseEntity<Department> findByOrg(
            @PathVariable(value = "org_id") Organization organization,
            @PathVariable(value = "dep_id") Long depId
    ) {
        List<Department> departmentList = depService.findAllByOrg(organization);
        Department department = departmentList.stream()
                .filter((dep) -> dep.getDepId().equals(depId)).toList().get(0);
        return ResponseEntity.ok(department);
    }

    @PostMapping("adduser")
    public ResponseEntity<Department> addUserToDep(@RequestParam Department depId, @RequestParam User user) {
        return ResponseEntity.ok(depService.addUser(depId.getDepId(), user));
    }

}
