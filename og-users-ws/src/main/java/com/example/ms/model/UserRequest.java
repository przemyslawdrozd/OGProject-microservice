package com.example.ms.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter @Setter
public class UserRequest {

    @NotNull(message = "Username needed")
    @Size(min = 2, message = "Name is to short")
    private String username;

    @NotNull(message = "Password needed")
    @Size(min = 5, max = 16, message = "Invalid Password")
    private String password;

    @NotNull(message = "email needed")
    @Email
    private String email;
}
