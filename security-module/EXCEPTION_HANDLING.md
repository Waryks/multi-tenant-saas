# Exception Handling in RegistrationService

## Overview

The `RegistrationService` has been improved with comprehensive exception handling to provide better error messages for REST calls and prevent application crashes.

## Custom Exception Classes

### RegistrationException (Base Class)
- **Purpose**: Base exception for all registration-related errors
- **Properties**: 
  - `message`: Human-readable error message
  - `statusCode`: HTTP status code for REST responses
- **Usage**: Extends `RuntimeException` and includes HTTP status code

### InvalidRequestException
- **Purpose**: Thrown when request data is invalid or missing required fields
- **Status Code**: 400 (Bad Request)
- **Examples**:
  - Null or empty token
  - Null registration request

### InvalidTokenException
- **Purpose**: Thrown when a registration token is invalid, expired, or already used
- **Status Code**: 400 (Bad Request)
- **Examples**:
  - Invalid JWT token format
  - Expired token
  - Token missing required claims (trainerId, email)
  - Invitation not found in database
  - Invitation already accepted

### ClientRegistrationException
- **Purpose**: Thrown when there's an error during client registration process
- **Status Code**: 500 (Internal Server Error)
- **Examples**:
  - Client registration service unavailable
  - Communication errors with client module
  - Unexpected errors during registration

## Global Exception Handler

The `GlobalExceptionHandler` automatically converts custom exceptions to appropriate HTTP responses:

```json
{
  "timestamp": "2024-01-15T10:30:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Token must not be null or empty",
  "path": "/api/register"
}
```

## Error Response Format

All error responses follow a consistent JSON format:
- `timestamp`: ISO 8601 timestamp of when the error occurred
- `status`: HTTP status code
- `error`: Error type/category
- `message`: Human-readable error message
- `path`: Request path (filled by framework)

## Logging

The service includes comprehensive logging:
- **Info**: Successful registrations
- **Warn**: Expected errors (invalid tokens, validation failures)
- **Error**: Unexpected errors and service communication failures

## Usage Example

```java
@POST
@Path("/register")
public Response registerClient(@QueryParam("token") String token, 
                             RegisterClientRequest request) {
    try {
        registrationService.registerClientFromToken(token, request);
        return Response.ok().build();
    } catch (InvalidRequestException e) {
        // Will be handled by GlobalExceptionHandler
        throw e;
    }
}
```

## Benefits

1. **Consistent Error Responses**: All errors return structured JSON responses
2. **Proper HTTP Status Codes**: Each error type maps to appropriate HTTP status
3. **No Application Crashes**: All exceptions are caught and handled gracefully
4. **Better Debugging**: Comprehensive logging for troubleshooting
5. **User-Friendly Messages**: Clear, actionable error messages
6. **Separation of Concerns**: Business logic separated from error handling 