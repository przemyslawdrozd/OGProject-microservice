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

    @ExceptionHandler(FacilitiesNotFoundException.class)
    public ResponseEntity<Object> handleFacilitiesException(FacilitiesNotFoundException e) {

        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}