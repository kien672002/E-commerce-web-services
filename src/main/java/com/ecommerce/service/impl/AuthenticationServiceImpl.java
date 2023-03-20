package com.ecommerce.service.impl;

import com.ecommerce.dto.UserLoginDTO;
import com.ecommerce.security.JWTProvider;
import com.ecommerce.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JWTProvider jwtProvider;

    @Override
    public String authenticateUser(UserLoginDTO userLoginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDTO.getUsername(),
                        userLoginDTO.getPassword()
                )
        );
        // Set authentication information to SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // no exception occur mean authenticate user was succeeded, otherwise exception will be caught by JwtAuthEntryPoint
        return jwtProvider.generateToken(authentication);
    }
}
