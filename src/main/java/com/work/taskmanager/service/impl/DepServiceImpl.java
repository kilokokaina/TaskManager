package com.work.taskmanager.service.impl;

import com.work.taskmanager.model.Department;
import com.work.taskmanager.model.Organization;
import com.work.taskmanager.model.User;
import com.work.taskmanager.model.dto.DepDTO;
import com.work.taskmanager.repository.DepRepository;
import com.work.taskmanager.service.DepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class DepServiceImpl implements DepService {

    private final DepRepository depRepository;

    @Autowired
    public DepServiceImpl(DepRepository depRepository) {
        this.depRepository = depRepository;
    }

    @Override
    public Department save(Department department) {
        return depRepository.save(department);
    }

    public Department create(DepDTO depDTO, Organization organization) {
        Department department = new Department();

        department.setShortName(depDTO.getShortName());
        department.setFullName(depDTO.getFullName());
        department.setOrganization(organization);

        return save(department);
    }

    public Department addUser(Long depId, User user) {
        Department department = findById(depId);

        Set<User> userSet = department.getUserSet();
        userSet.add(user);
        department.setUserSet(userSet);

        return save(department);
    }

    @Override
    public Department findById(Long depId) {
        return depRepository.findById(depId).orElse(null);
    }

    @Override
    public List<Department> findAllByOrg(Organization organization) {
        return depRepository.findAllByOrganization(organization);
    }

    @Override
    public Set<User> findAllUsers(Long depId) {
        return findById(depId).getUserSet();
    }

    @Override
    public void delete(Department department) {
        depRepository.delete(department);
    }

    @Override
    public void deleteById(Long depId) {
        depRepository.deleteById(depId);
    }
}
