package com.work.taskmanager.api;

import com.work.taskmanager.model.Organization;
import com.work.taskmanager.service.impl.OrgServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/org")
public class OrgAPI {

    private final OrgServiceImpl orgService;

    @Autowired
    public OrgAPI(OrgServiceImpl orgService) {
        this.orgService = orgService;
    }

    @GetMapping
    public ResponseEntity<List<Organization>> findAll() {
        return ResponseEntity.ok(orgService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Organization> findById(@PathVariable(value = "id") Organization organization) {
        return ResponseEntity.ok(organization);
    }

    @PostMapping
    public ResponseEntity<Organization> addOrg(@RequestBody Organization organization) {
        return ResponseEntity.ok(orgService.save(organization));
    }

}
