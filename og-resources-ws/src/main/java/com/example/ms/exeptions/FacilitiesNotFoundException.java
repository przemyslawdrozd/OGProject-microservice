package com.example.ms.exeptions;

public class FacilitiesNotFoundException extends RuntimeException {
    public FacilitiesNotFoundException(String message) {
        super(message);
    }
}
