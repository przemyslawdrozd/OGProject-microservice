package com.example.ms.exception;

import com.example.ms.exception.buildings.FacilitiesException;
import com.example.ms.exception.resources.ResourcesNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class FacilitiesExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FacilitiesException.class)
    public ResponseEntity<Object> handleFacilitiesException(FacilitiesException e) {
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(ResourcesNotFoundException.class)
    public ResponseEntity<Object> handleResourcesServiceException(ResourcesNotFoundException e) {
        logger.error("ResourcesNotFoundException:: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

}
