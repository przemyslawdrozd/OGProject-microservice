package com.example.ms.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.Date;

@RestControllerAdvice
public class ResourcesExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourcesException.class)
    public ResponseEntity<Object> handleResourceException(ResourcesException e) {

        ErrorMessage errorMessage = new ErrorMessage(new Date(), e.getMessage());
        logger.error("Error message: " + errorMessage.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}