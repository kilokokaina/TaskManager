package com.work.taskmanager.repository;

import com.work.taskmanager.model.Organization;
import com.work.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface OrgRepository extends JpaRepository<Organization, Long> {
}
