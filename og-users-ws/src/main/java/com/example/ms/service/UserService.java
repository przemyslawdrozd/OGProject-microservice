package com.example.ms.service;

import com.example.ms.model.UserRequest;
import com.example.ms.model.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserResponse createUser(UserRequest userRequest);

    UserResponse getUserByUserId(String userId);

    UserResponse getUserDetailsByEmail(String email);
}
