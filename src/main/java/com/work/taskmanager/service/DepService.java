package com.work.taskmanager.service;

import com.work.taskmanager.model.Department;
import com.work.taskmanager.model.Organization;
import com.work.taskmanager.model.User;

import java.util.List;
import java.util.Set;

public interface DepService {

    Department save(Department department);
    Department findById(Long depId);
    List<Department> findAllByOrg(Organization organization);
    Set<User> findAllUsers(Long depId);
    void delete(Department department);
    void deleteById(Long depId);

}
