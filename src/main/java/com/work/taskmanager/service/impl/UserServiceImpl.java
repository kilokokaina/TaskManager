package com.work.taskmanager.service.impl;

import com.work.taskmanager.exception.UserAlreadyExistException;
import com.work.taskmanager.model.Task;
import com.work.taskmanager.model.User;
import com.work.taskmanager.repository.UserRepository;
import com.work.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User create(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistException(String.format("User %s already exist", user.getUsername()));
        }
        return userRepository.save(user);
    }

    public void addTask(User user, Task task) {
        List<Task> userTasks = user.getTaskList();

        userTasks.add(task);
        user.setTaskList(userTasks);

        this.save(user);
    }

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

}
