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
import io.smallrye.jwt.auth.principal.ParseException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.util.Optional;

@ApplicationScoped
public class RegistrationService {

    private static final Logger LOG = Logger.getLogger(RegistrationService.class);

    @Inject
    JwtService jwtService;

    @Inject
    InviteRepository repository;

    @Inject
    @RestClient
    ClientRegistrationClient clientRegistrationClient;

    /**
     * Registers a client using an invitation token.
     * 
     * @param token The invitation token
     * @param request The client registration request
     * @throws InvalidRequestException if the request data is invalid
     * @throws InvalidTokenException if the token is invalid, expired, or already used
     * @throws ClientRegistrationException if there's an error during client registration
     */
    public void registerClientFromToken(String token, RegisterClientRequest request) {
        try {
            // Validate input parameters
            validateInputParameters(token, request);
            
            // Parse and validate JWT token
            JwtInviteClaims claims = parseAndValidateToken(token);
            
            // Validate and update invite status
            validateAndUpdateInvite(token);
            
            // Register client
            registerClient(claims, request);
            
            LOG.infof("Successfully registered client for email: %s with trainer: %s", 
                     claims.getEmail(), claims.getTrainerId());
            
        } catch (InvalidRequestException | InvalidTokenException | ClientRegistrationException e) {
            // Re-throw our custom exceptions as they already have proper status codes
            throw e;
        } catch (ParseException e) {
            LOG.warnf("Failed to parse JWT token: %s", e.getMessage());
            throw new InvalidTokenException("Invalid or expired invitation token", e);
        } catch (WebApplicationException e) {
            LOG.warnf("WebApplicationException during client registration: %s", e.getMessage());
            throw new ClientRegistrationException("Failed to register client: " + e.getMessage(), e);
        } catch (Exception e) {
            LOG.errorf("Unexpected error during client registration: %s", e.getMessage(), e);
            throw new ClientRegistrationException("An unexpected error occurred during registration", e);
        }
    }
    
    private void validateInputParameters(String token, RegisterClientRequest request) {
        if (token == null || token.trim().isEmpty()) {
            throw new InvalidRequestException("Token must not be null or empty");
        }
        if (request == null) {
            throw new InvalidRequestException("Registration request must not be null");
        }
    }
    
    private JwtInviteClaims parseAndValidateToken(String token) throws ParseException {
        JwtInviteClaims claims = jwtService.parseInviteToken(token);
        
        if (claims.getTrainerId() == null) {
            throw new InvalidTokenException("Token is missing trainer ID");
        }
        if (claims.getEmail() == null || claims.getEmail().trim().isEmpty()) {
            throw new InvalidTokenException("Token is missing email address");
        }
        
        return claims;
    }
    
    private void validateAndUpdateInvite(String token) {
        Optional<PendingInviteEntity> inviteOpt = repository.findByToken(token);
        
        if (inviteOpt.isEmpty()) {
            throw new InvalidTokenException("Invitation token not found");
        }
        
        PendingInviteEntity invite = inviteOpt.get();
        if (invite.isAccepted()) {
            throw new InvalidTokenException("Invitation token has already been used");
        }
        
        // Mark invite as accepted
        invite.setAccepted(true);
        repository.persist(invite);

    }
    
    private void registerClient(JwtInviteClaims claims, RegisterClientRequest request) {
        try {
            clientRegistrationClient.createClientFromInvite(
                    claims.getTrainerId(),
                    claims.getEmail(),
                    request
            );
        } catch (WebApplicationException e) {
            LOG.errorf("Client registration service error: %s", e.getMessage());
            throw new ClientRegistrationException("Failed to create client account: " + e.getMessage(), e);
        } catch (Exception e) {
            LOG.errorf("Unexpected error calling client registration service: %s", e.getMessage(), e);
            throw new ClientRegistrationException("Failed to communicate with client registration service", e);
        }
    }
}
