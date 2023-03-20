package com.ecommerce.service;

import com.ecommerce.dto.UserLoginDTO;

public interface AuthenticationService {
    String authenticateUser(UserLoginDTO userLoginDTO);
}
