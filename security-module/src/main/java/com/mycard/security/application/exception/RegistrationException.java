package com.mycard.security.application.exception;

import lombok.Getter;

/**
 * Custom exception for registration-related errors.
 * This exception is designed to be caught and converted to appropriate HTTP responses.
 */
@Getter
public class RegistrationException extends RuntimeException {
    
    private final int statusCode;
    
    public RegistrationException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
    
    public RegistrationException(String message, int statusCode, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

}