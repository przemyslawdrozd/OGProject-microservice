package com.example.ms.exception;

public class CreateUserException extends RuntimeException {

    public CreateUserException(String message) {
        super(message);
    }
}