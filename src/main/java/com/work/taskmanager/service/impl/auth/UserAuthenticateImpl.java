package com.work.taskmanager.service.impl.auth;

import com.work.taskmanager.exception.UserAlreadyExistException;
import com.work.taskmanager.model.User;
import com.work.taskmanager.model.dto.UserDTO;
import com.work.taskmanager.service.UserAuthentication;
import com.work.taskmanager.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class UserAuthenticateImpl implements UserAuthentication {

    private final SecurityContextRepository contextRepository;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final HttpServletResponse response;
    private final HttpServletRequest request;

    @Autowired
    public UserAuthenticateImpl(AuthenticationManager authenticationManager, UserServiceImpl userService,
                                HttpServletResponse response, HttpServletRequest request) {
        this.contextRepository = new HttpSessionSecurityContextRepository();
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.response = response;
        this.request = request;
    }

    @Override
    public User register(UserDTO credentials) throws UserAlreadyExistException {
        String username = credentials.username;
        String password = credentials.password;

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        return userService.create(user);
    }

    @Override
    public HttpStatus startSession(String username, String password) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        authentication = authenticationManager.authenticate(authentication);

        HttpStatus status = (!Objects.isNull(authentication)) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;

        if (!Objects.isNull(authentication)) {
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);

            contextRepository.saveContext(context, request, response);

            log.info(request.getRemoteAddr());
        }

        return status;
    }

}
