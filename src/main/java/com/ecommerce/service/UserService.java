package com.ecommerce.service;

import com.ecommerce.dto.UserCreationDTO;
import com.ecommerce.dto.UserDTO;
import com.ecommerce.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface UserService {
    List<UserDTO> findAll();

    UserDTO save(UserCreationDTO userCreationDTO) throws DataIntegrityViolationException;

    UserDTO findById(Long id);

    User findByUsername(String username);

    void deleteById(Long id) throws EntityNotFoundException;
}
