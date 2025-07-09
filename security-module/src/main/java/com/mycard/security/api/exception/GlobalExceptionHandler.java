package com.mycard.security.api.exception;

import com.mycard.security.application.exception.ClientRegistrationException;
import com.mycard.security.application.exception.InvalidRequestException;
import com.mycard.security.application.exception.InvalidTokenException;
import com.mycard.security.application.exception.RegistrationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler that converts custom exceptions to appropriate HTTP responses.
 */
@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    private static final Logger LOG = Logger.getLogger(GlobalExceptionHandler.class);

    @Override
    public Response toResponse(Exception exception) {
        LOG.debugf("Handling exception: %s", exception.getMessage());
        
        if (exception instanceof RegistrationException) {
            return handleRegistrationException((RegistrationException) exception);
        }
        
        // Handle other exceptions
        LOG.errorf("Unhandled exception: %s", exception.getMessage(), exception);
        return createErrorResponse(
                "Internal server error",
                "An unexpected error occurred",
                500
        );
    }
    
    private Response handleRegistrationException(RegistrationException exception) {
        int statusCode = exception.getStatusCode();
        String message = exception.getMessage();
        
        if (exception instanceof InvalidRequestException) {
            return createErrorResponse("Bad Request", message, statusCode);
        } else if (exception instanceof InvalidTokenException) {
            return createErrorResponse("Invalid Token", message, statusCode);
        } else if (exception instanceof ClientRegistrationException) {
            return createErrorResponse("Registration Error", message, statusCode);
        } else {
            return createErrorResponse("Registration Error", message, statusCode);
        }
    }
    
    private Response createErrorResponse(String error, String message, int statusCode) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", Instant.now().toString());
        errorResponse.put("status", statusCode);
        errorResponse.put("error", error);
        errorResponse.put("message", message);
        errorResponse.put("path", ""); // Will be filled by the framework
        
        return Response.status(statusCode)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
} 