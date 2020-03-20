package com.example.ms.service;

import com.example.ms.model.UserRequest;
import com.example.ms.model.UserResponse;

public interface UserService
{

    UserResponse createUser(UserRequest userRequest);

    UserResponse getUserDetailsByEmail(String email);

    UserResponse getUserByUserId(String userId);
}
