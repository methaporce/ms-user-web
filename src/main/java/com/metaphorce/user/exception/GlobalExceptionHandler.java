package com.metaphorce.user.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<String> handleRateLimitExceededException(RateLimitExceededException ex) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(ex.getMessage());
    }
    @ExceptionHandler(BadRequestUserException.class)
    public ResponseEntity<String> handleBadRequestUserException(BadRequestUserException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleGenericException(Throwable ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Service is temporarily unavailable. Please try again later.");
    }

}
