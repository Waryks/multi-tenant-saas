package com.mycard.security.application.exception;

/**
 * Exception thrown when request data is invalid or missing required fields.
 */
public class InvalidRequestException extends RegistrationException {
    
    public InvalidRequestException(String message) {
        super(message, 400);
    }
    
    public InvalidRequestException(String message, Throwable cause) {
        super(message, 400, cause);
    }
} 