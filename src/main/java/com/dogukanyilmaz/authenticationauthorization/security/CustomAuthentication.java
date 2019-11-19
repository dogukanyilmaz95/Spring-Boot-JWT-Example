package com.dogukanyilmaz.authenticationauthorization.security;

import com.dogukanyilmaz.authenticationauthorization.entity.User;
import com.dogukanyilmaz.authenticationauthorization.repository.RoleRepository;
import com.dogukanyilmaz.authenticationauthorization.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dogukanyilmaz
 */
@Component
public class CustomAuthentication implements AuthenticationProvider {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    private User doLogin(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        User user = doLogin(name, password);
        if (user != null) {
            return new UsernamePasswordAuthenticationToken(name, password, roleRepository.getRolesByUsers(user));
        } else {
            throw new BadCredentialsException("Password/Username Exception");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
