package com.example.ms.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class UserResponse {

    private String userId;
    private String username;
    private String email;
}
