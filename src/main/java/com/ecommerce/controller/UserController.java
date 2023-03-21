package com.ecommerce.controller;

import com.ecommerce.model.ResponseObject;
import com.ecommerce.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@PreAuthorize(value = "hasAuthority('ADMIN')")
public class UserController {
    final
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    ResponseEntity<ResponseObject> getUsers() {
        return ResponseEntity
                .ok()
                .body(new ResponseObject("Query all user successfully", userService.findAll()));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteById(id);
            return ResponseEntity
                    .ok()
                    .body(new ResponseObject("Delete user successfully", null));
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject(e.getMessage(), null));
        }
    }

}
