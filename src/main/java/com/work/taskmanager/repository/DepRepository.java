package com.work.taskmanager.repository;

import com.work.taskmanager.model.Department;
import com.work.taskmanager.model.Organization;
import com.work.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepRepository extends JpaRepository<Department, Long> {

    List<Department> findAllByOrganization(Organization organization);

}
