package com.work.taskmanager.service;

import com.work.taskmanager.model.User;

public interface UserService {

    User save(User user);
    User create(User user);
    User findById(Long userId);
    User findByUsername(String username);

}
