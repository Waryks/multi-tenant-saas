package com.mycard.security.application.exception;

/**
 * Exception thrown when a registration token is invalid, expired, or already used.
 */
public class InvalidTokenException extends RegistrationException {
    
    public InvalidTokenException(String message) {
        super(message, 400);
    }
    
    public InvalidTokenException(String message, Throwable cause) {
        super(message, 400, cause);
    }
} 