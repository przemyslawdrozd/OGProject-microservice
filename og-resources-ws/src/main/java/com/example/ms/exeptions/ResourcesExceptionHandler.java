package com.example.ms.exeptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.Date;

@RestControllerAdvice
public class ResourcesExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(ResourcesException.class)
    public ResponseEntity<Object> handleItemNotFoundException(ResourcesException ex) {

        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
        LOG.error("Error message: {}", errorMessage.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}