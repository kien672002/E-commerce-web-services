package com.ecommerce.mapper;

import com.ecommerce.dto.UserCreationDTO;
import com.ecommerce.dto.UserDTO;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    static public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFullName(user.getFullName());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        userDTO.setRoles(user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toList())
        );
        return userDTO;
    }

    static public User toEntity(UserCreationDTO userCreationDTO, List<Role> roles) {
        User user = new User();
        user.setId(userCreationDTO.getId());
        user.setEmail(userCreationDTO.getEmail());
        user.setUsername(userCreationDTO.getUsername());
        user.setPassword(userCreationDTO.getPassword());
        user.setRoles(roles);
        return user;
    }
}
