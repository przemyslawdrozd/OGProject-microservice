package com.example.ms.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users-api")
public class UserController {

    @GetMapping
    public String test() {
        return "test - users-api";
    }
}
