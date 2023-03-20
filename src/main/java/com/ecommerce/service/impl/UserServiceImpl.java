package com.ecommerce.service.impl;

import com.ecommerce.dto.UserCreationDTO;
import com.ecommerce.dto.UserDTO;
import com.ecommerce.mapper.UserMapper;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepositpry;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO save(UserCreationDTO userCreationDTO) throws DataIntegrityViolationException {
        userCreationDTO.setPassword(passwordEncoder.encode(userCreationDTO.getPassword()));
        Role role = roleRepositpry.findByName("USER");
        User user = UserMapper.toEntiy(userCreationDTO, Collections.singletonList(role));
        return UserMapper.toDTO(userRepository.save(user));
    }

    @Override
    public UserDTO findById(Long id) throws EntityNotFoundException {
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isEmpty())
            throw new EntityNotFoundException("Not found user with id=" + id);
        return foundUser.map(UserMapper::toDTO).get();
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        Optional<User> foundUser = userRepository.findByUsername(username);
        if (foundUser.isEmpty())
            throw new UsernameNotFoundException("Not found user with username=" + username);
        return foundUser.get();
    }

    @Override
    public void deleteById(Long id) throws EntityNotFoundException {
        userRepository.deleteById(this.findById(id).getId());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.findByUsername(username);
    }
}
