package com.example.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<Void> handleDuplicateUsername(){
        return ResponseEntity.status(409).build();
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Void> handleInvalidLogin(){
        return ResponseEntity.status(401).build();
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Void> handleValidationFailure(){
        return ResponseEntity.status(400).build();
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<Void> handleGenericException(Exception e){
        e.printStackTrace();
        return ResponseEntity.status(500).build();
    }

}
