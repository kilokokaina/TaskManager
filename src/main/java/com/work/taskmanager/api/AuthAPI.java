package com.work.taskmanager.api;

import com.work.taskmanager.exception.UserAlreadyExistException;
import com.work.taskmanager.model.User;
import com.work.taskmanager.model.dto.UserDTO;
import com.work.taskmanager.service.impl.UserServiceImpl;
import com.work.taskmanager.service.impl.auth.UserAuthenticateImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("auth")
public class AuthAPI {

    private final UserAuthenticateImpl userAuthenticate;
    private final UserServiceImpl userService;

    @Autowired
    public AuthAPI(UserAuthenticateImpl userAuthenticate, UserServiceImpl userService) {
        this.userAuthenticate = userAuthenticate;
        this.userService = userService;
    }

    @PostMapping("rest")
    public ResponseEntity<User> authREST(@RequestBody UserDTO credentials) {
        HttpStatus status = userAuthenticate.startSession(credentials.username, credentials.password);
        return ResponseEntity.status(status).body(userService.findByUsername(credentials.username));
    }

    @PostMapping("reg")
    public ResponseEntity<User> regUser(@RequestBody UserDTO userDTO) {
        User user;
        try {
            user = userAuthenticate.register(userDTO);
        } catch (UserAlreadyExistException exception) {
            log.error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("sch")
    public ResponseEntity<String> sch() {
        return ResponseEntity.ok(SecurityContextHolder.getContext().toString());
    }

}
