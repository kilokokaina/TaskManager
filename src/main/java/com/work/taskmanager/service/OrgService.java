package com.work.taskmanager.service;

import com.work.taskmanager.model.Department;
import com.work.taskmanager.model.Organization;
import com.work.taskmanager.model.User;

import java.util.List;
import java.util.Set;

public interface OrgService {

    Organization save(Organization organization);
    List<Organization> findAll();
    List<Department> findAllDeps(Long orgId);
    Set<User> findAllEmployees(Long orgId);
    void delete(Organization organization);
    void deleteById(Long orgId);

}
