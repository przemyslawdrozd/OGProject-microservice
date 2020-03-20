package com.example.ms.api;

import com.example.ms.model.UserRequest;
import com.example.ms.model.UserResponse;
import com.example.ms.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users-api")
public class UserController {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        LOG.info("Start to create new user: {}", userRequest.getEmail());
        UserResponse userResponse = userService.createUser(userRequest);

        LOG.info("Return user response");
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }
}
