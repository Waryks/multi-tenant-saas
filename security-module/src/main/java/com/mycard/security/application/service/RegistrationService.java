package com.mycard.security.application.service;

import com.mycard.security.acl.client.ClientRegistrationClient;
import com.mycard.security.api.dto.RegisterClientRequest;
import com.mycard.security.domain.model.JwtInviteClaims;
import com.mycard.security.infrastructure.jwt.JwtService;
import com.mycard.security.infrastructure.persistence.InviteRepository;
import com.mycard.security.infrastructure.persistence.PendingInviteEntity;
import io.smallrye.jwt.auth.principal.ParseException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Optional;

@ApplicationScoped
public class RegistrationService {

    @Inject
    JwtService jwtService;

    @Inject
    InviteRepository repository;

    @Inject
    @RestClient
    ClientRegistrationClient clientRegistrationClient;

    public void registerClientFromToken(String token, RegisterClientRequest request) throws ParseException {
        JwtInviteClaims claims = jwtService.parseInviteToken(token);

        Optional<PendingInviteEntity> inviteOpt = repository.findByToken(token);
        if (inviteOpt.isEmpty() || inviteOpt.get().isAccepted()) {
            throw new WebApplicationException("Invalid or used token", 400);
        }

        PendingInviteEntity invite = inviteOpt.get();
        invite.setAccepted(true);
        repository.persist(invite);

        clientRegistrationClient.createClientFromInvite(
                claims.getTrainerId(),
                claims.getEmail(),
                request
        );
    }
}
