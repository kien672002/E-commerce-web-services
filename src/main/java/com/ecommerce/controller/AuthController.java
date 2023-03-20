package com.ecommerce.controller;

import com.ecommerce.dto.UserCreationDTO;
import com.ecommerce.dto.UserLoginDTO;
import com.ecommerce.model.ResponseObject;
import com.ecommerce.service.AuthenticationService;
import com.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
public class AuthController {
    @Autowired
    UserService userService;
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    ResponseEntity<ResponseObject> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        return ResponseEntity
                .ok()
                .body(new ResponseObject("Login successfully", authenticationService.authenticateUser(userLoginDTO)));
    }

    @GetMapping("")
    ResponseEntity<ResponseObject> getAuth() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            return ResponseEntity
                    .ok()
                    .body(new ResponseObject("Authentication successfully",
                            authentication.getAuthorities()
                                    .stream()
                                    .map(GrantedAuthority::getAuthority)
                                    .collect(Collectors.toList())));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseObject("Authenticaton failed", null));
        }
    }

    @PostMapping("/register")
    ResponseEntity<ResponseObject> save(@Valid @RequestBody UserCreationDTO userCreationDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseObject("Create new user successfully", userService.save(userCreationDTO)));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseObject("Email has been used", null));
        }
    }


}
