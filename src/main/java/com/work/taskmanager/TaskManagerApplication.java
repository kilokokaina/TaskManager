package com.work.taskmanager;

import com.work.taskmanager.model.Department;
import com.work.taskmanager.model.Organization;
import com.work.taskmanager.model.User;
import com.work.taskmanager.service.impl.DepServiceImpl;
import com.work.taskmanager.service.impl.OrgServiceImpl;
import com.work.taskmanager.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.*;

@Slf4j
@SpringBootApplication
public class TaskManagerApplication {

    private final UserServiceImpl userService;
    private final DepServiceImpl depService;
    private final OrgServiceImpl orgService;

    @Autowired
    public TaskManagerApplication(UserServiceImpl userService, DepServiceImpl depService, OrgServiceImpl orgService) {
        this.userService = userService;
        this.depService = depService;
        this.orgService = orgService;
    }

    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
    }

    @Bean
    public CommandLineRunner cmd() {
        return args -> {
            List<Organization> organizationList = orgService.findAll();
            for (Organization organization : organizationList) {
                String orgUUID = UUID.randomUUID().toString();
                organization.setOrgUUID(orgUUID);
                orgService.save(organization);
            }
        };
    }

}
