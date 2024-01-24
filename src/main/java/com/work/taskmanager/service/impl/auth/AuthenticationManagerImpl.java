package com.work.taskmanager.service.impl.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthenticationManagerImpl implements AuthenticationManager {

    private final AuthenticationProviderImpl authenticationProvider;

    @Autowired
    public AuthenticationManagerImpl(AuthenticationProviderImpl authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info(this.getClass().getSimpleName());
        authentication = authenticationProvider.authenticate(authentication);
        return authentication;
    }

}
