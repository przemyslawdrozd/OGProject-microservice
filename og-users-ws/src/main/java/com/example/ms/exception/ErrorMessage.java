package com.example.ms.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Date;

@NoArgsConstructor
@Getter
public class ErrorMessage {

    private Date timestamp;
    private String message;

    public ErrorMessage(Date timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }
}