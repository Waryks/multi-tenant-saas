package com.mycard.security.application.exception;

/**
 * Exception thrown when there's an error during client registration process.
 */
public class ClientRegistrationException extends RegistrationException {
    
    public ClientRegistrationException(String message) {
        super(message, 500);
    }
    
    public ClientRegistrationException(String message, Throwable cause) {
        super(message, 500, cause);
    }
} 