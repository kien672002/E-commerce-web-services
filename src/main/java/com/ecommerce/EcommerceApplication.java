package com.ecommerce;

import com.ecommerce.dto.UserCreationDTO;
import com.ecommerce.mapper.UserMapper;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class EcommerceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        try {
            UserCreationDTO userCreationDTO = new UserCreationDTO();

            userCreationDTO.setUsername("admin");
            userCreationDTO.setPassword("admin");
            userCreationDTO.setEmail("admin@admin.com");

            userCreationDTO.setPassword(passwordEncoder.encode(userCreationDTO.getPassword()));
            List<Role> roles = List.of(roleRepository.findByName("ADMIN"), roleRepository.findByName("USER"));
            User user = UserMapper.toEntity(userCreationDTO, roles);
            userRepository.save(user);
        } catch (Exception e) {
            // If admin has been created, do nothing
        }
    }
}
