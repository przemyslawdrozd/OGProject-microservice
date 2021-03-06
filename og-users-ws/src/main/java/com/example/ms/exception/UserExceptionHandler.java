package com.example.ms.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.Date;

@ControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), "Invalid user data");
        LOG.error("Error message: {}", errorMessage.getMessage());
        return ResponseEntity.status(status).body(errorMessage);
    }

    @ExceptionHandler(CreateUserException.class)
    public ResponseEntity<Object> handleItemNotFoundException(CreateUserException ex) {

        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
        LOG.error("Error message: {}", errorMessage.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}