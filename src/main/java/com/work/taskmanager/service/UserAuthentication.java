package com.work.taskmanager.service;

import com.work.taskmanager.model.User;
import com.work.taskmanager.model.dto.UserDTO;
import org.springframework.http.HttpStatus;

public interface UserAuthentication {

    User register(UserDTO credentials);
    HttpStatus startSession(String username, String password);

}
