package com.work.taskmanager.service.impl;

import com.work.taskmanager.model.Department;
import com.work.taskmanager.model.Organization;
import com.work.taskmanager.model.User;
import com.work.taskmanager.repository.OrgRepository;
import com.work.taskmanager.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrgServiceImpl implements OrgService {

    private final OrgRepository orgRepository;
    private final DepServiceImpl depService;

    @Autowired
    public OrgServiceImpl(OrgRepository orgRepository, DepServiceImpl depService) {
        this.orgRepository = orgRepository;
        this.depService = depService;
    }

    @Override
    public Organization save(Organization organization) {
        return orgRepository.save(organization);
    }

    @Override
    public List<Organization> findAll() {
        return orgRepository.findAll();
    }

    public Organization findById(Long orgId) {
        return orgRepository.findById(orgId).orElse(null);
    }

    @Override
    public List<Department> findAllDeps(Long orgId) {
        return depService.findAllByOrg(findById(orgId));
    }

    @Override
    public Set<User> findAllEmployees(Long orgId) {
        List<Department> departmentList = depService.findAllByOrg(findById(orgId));
        Set<User> userSet = new HashSet<>();

        for (Department department : departmentList) {
            Set<User> depUserSet = department.getUserSet();
            userSet.addAll(depUserSet);
        }

        return userSet;
    }

    @Override
    public void delete(Organization organization) {
        orgRepository.delete(organization);
    }

    @Override
    public void deleteById(Long orgId) {
        orgRepository.deleteById(orgId);
    }
}
