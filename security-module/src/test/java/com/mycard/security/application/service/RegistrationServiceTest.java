package com.mycard.security.application.service;

import com.mycard.security.acl.client.ClientRegistrationClient;
import com.mycard.security.api.dto.RegisterClientRequest;
import com.mycard.security.application.exception.ClientRegistrationException;
import com.mycard.security.application.exception.InvalidRequestException;
import com.mycard.security.application.exception.InvalidTokenException;
import com.mycard.security.domain.model.JwtInviteClaims;
import com.mycard.security.infrastructure.jwt.JwtService;
import com.mycard.security.infrastructure.persistence.InviteRepository;
import com.mycard.security.infrastructure.persistence.PendingInviteEntity;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.InjectMock;
import io.smallrye.jwt.auth.principal.ParseException;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@QuarkusTest
class RegistrationServiceTest {

    @Inject
    RegistrationService registrationService;

    @InjectMock
    JwtService jwtService;

    @InjectMock
    InviteRepository repository;

    @InjectMock
    @RestClient
    ClientRegistrationClient clientRegistrationClient;

    @Test
    void testRegisterClientFromToken_Success() throws ParseException {
        // Arrange
        String token = "valid-token";
        RegisterClientRequest request = new RegisterClientRequest();
        UUID trainerId = UUID.randomUUID();
        String email = "test@example.com";
        
        JwtInviteClaims claims = new JwtInviteClaims(trainerId, email);
        PendingInviteEntity invite = new PendingInviteEntity();
        invite.setAccepted(false);
        
        when(jwtService.parseInviteToken(token)).thenReturn(claims);
        when(repository.findByToken(token)).thenReturn(Optional.of(invite));
        doNothing().when(clientRegistrationClient).createClientFromInvite(any(), anyString(), any());
        
        // Act & Assert
        assertDoesNotThrow(() -> registrationService.registerClientFromToken(token, request));
        
        verify(repository).persist(invite);
        verify(clientRegistrationClient).createClientFromInvite(trainerId, email, request);
    }

    @Test
    void testRegisterClientFromToken_NullToken() {
        // Arrange
        RegisterClientRequest request = new RegisterClientRequest();
        
        // Act & Assert
        InvalidRequestException exception = assertThrows(
                InvalidRequestException.class,
                () -> registrationService.registerClientFromToken(null, request)
        );
        
        assertEquals("Token must not be null or empty", exception.getMessage());
        assertEquals(400, exception.getStatusCode());
    }

    @Test
    void testRegisterClientFromToken_EmptyToken() {
        // Arrange
        RegisterClientRequest request = new RegisterClientRequest();
        
        // Act & Assert
        InvalidRequestException exception = assertThrows(
                InvalidRequestException.class,
                () -> registrationService.registerClientFromToken("", request)
        );
        
        assertEquals("Token must not be null or empty", exception.getMessage());
        assertEquals(400, exception.getStatusCode());
    }

    @Test
    void testRegisterClientFromToken_NullRequest() {
        // Arrange
        String token = "valid-token";
        
        // Act & Assert
        InvalidRequestException exception = assertThrows(
                InvalidRequestException.class,
                () -> registrationService.registerClientFromToken(token, null)
        );
        
        assertEquals("Registration request must not be null", exception.getMessage());
        assertEquals(400, exception.getStatusCode());
    }

    @Test
    void testRegisterClientFromToken_InvalidToken() throws ParseException {
        // Arrange
        String token = "invalid-token";
        RegisterClientRequest request = new RegisterClientRequest();
        
        when(jwtService.parseInviteToken(token)).thenThrow(new ParseException("Invalid token"));
        
        // Act & Assert
        InvalidTokenException exception = assertThrows(
                InvalidTokenException.class,
                () -> registrationService.registerClientFromToken(token, request)
        );
        
        assertEquals("Invalid or expired invitation token", exception.getMessage());
        assertEquals(400, exception.getStatusCode());
    }

    @Test
    void testRegisterClientFromToken_MissingTrainerId() throws ParseException {
        // Arrange
        String token = "valid-token";
        RegisterClientRequest request = new RegisterClientRequest();
        String email = "test@example.com";
        
        JwtInviteClaims claims = new JwtInviteClaims(null, email);
        
        when(jwtService.parseInviteToken(token)).thenReturn(claims);
        
        // Act & Assert
        InvalidTokenException exception = assertThrows(
                InvalidTokenException.class,
                () -> registrationService.registerClientFromToken(token, request)
        );
        
        assertEquals("Token is missing trainer ID", exception.getMessage());
        assertEquals(400, exception.getStatusCode());
    }

    @Test
    void testRegisterClientFromToken_MissingEmail() throws ParseException {
        // Arrange
        String token = "valid-token";
        RegisterClientRequest request = new RegisterClientRequest();
        UUID trainerId = UUID.randomUUID();
        
        JwtInviteClaims claims = new JwtInviteClaims(trainerId, null);
        
        when(jwtService.parseInviteToken(token)).thenReturn(claims);
        
        // Act & Assert
        InvalidTokenException exception = assertThrows(
                InvalidTokenException.class,
                () -> registrationService.registerClientFromToken(token, request)
        );
        
        assertEquals("Token is missing email address", exception.getMessage());
        assertEquals(400, exception.getStatusCode());
    }

    @Test
    void testRegisterClientFromToken_InviteNotFound() throws ParseException {
        // Arrange
        String token = "valid-token";
        RegisterClientRequest request = new RegisterClientRequest();
        UUID trainerId = UUID.randomUUID();
        String email = "test@example.com";
        
        JwtInviteClaims claims = new JwtInviteClaims(trainerId, email);
        
        when(jwtService.parseInviteToken(token)).thenReturn(claims);
        when(repository.findByToken(token)).thenReturn(Optional.empty());
        
        // Act & Assert
        InvalidTokenException exception = assertThrows(
                InvalidTokenException.class,
                () -> registrationService.registerClientFromToken(token, request)
        );
        
        assertEquals("Invitation token not found", exception.getMessage());
        assertEquals(400, exception.getStatusCode());
    }

    @Test
    void testRegisterClientFromToken_AlreadyAccepted() throws ParseException {
        // Arrange
        String token = "valid-token";
        RegisterClientRequest request = new RegisterClientRequest();
        UUID trainerId = UUID.randomUUID();
        String email = "test@example.com";
        
        JwtInviteClaims claims = new JwtInviteClaims(trainerId, email);
        PendingInviteEntity invite = new PendingInviteEntity();
        invite.setAccepted(true);
        
        when(jwtService.parseInviteToken(token)).thenReturn(claims);
        when(repository.findByToken(token)).thenReturn(Optional.of(invite));
        
        // Act & Assert
        InvalidTokenException exception = assertThrows(
                InvalidTokenException.class,
                () -> registrationService.registerClientFromToken(token, request)
        );
        
        assertEquals("Invitation token has already been used", exception.getMessage());
        assertEquals(400, exception.getStatusCode());
    }

    @Test
    void testRegisterClientFromToken_ClientRegistrationError() throws ParseException {
        // Arrange
        String token = "valid-token";
        RegisterClientRequest request = new RegisterClientRequest();
        UUID trainerId = UUID.randomUUID();
        String email = "test@example.com";
        
        JwtInviteClaims claims = new JwtInviteClaims(trainerId, email);
        PendingInviteEntity invite = new PendingInviteEntity();
        invite.setAccepted(false);
        
        when(jwtService.parseInviteToken(token)).thenReturn(claims);
        when(repository.findByToken(token)).thenReturn(Optional.of(invite));
        doThrow(new WebApplicationException("Service unavailable", 503))
                .when(clientRegistrationClient).createClientFromInvite(any(), anyString(), any());
        
        // Act & Assert
        ClientRegistrationException exception = assertThrows(
                ClientRegistrationException.class,
                () -> registrationService.registerClientFromToken(token, request)
        );
        
        assertEquals("Failed to create client account: Service unavailable", exception.getMessage());
        assertEquals(500, exception.getStatusCode());
    }
} 