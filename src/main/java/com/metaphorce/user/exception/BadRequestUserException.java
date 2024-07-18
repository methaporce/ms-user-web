package com.metaphorce.user.exception;

public class BadRequestUserException extends RuntimeException {
    public BadRequestUserException(String message) {
        super(message);
    }
}
