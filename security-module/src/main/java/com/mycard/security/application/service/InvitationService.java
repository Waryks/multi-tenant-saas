package com.mycard.security.application.service;

import com.mycard.security.acl.client.ClientRegistrationClient;
import com.mycard.security.application.command.InviteClientCommand;
import com.mycard.security.application.event.IClientInviteEventPublisher;
import com.mycard.security.domain.event.ClientInvitedEvent;
import com.mycard.security.infrastructure.jwt.JwtService;
import com.mycard.security.infrastructure.persistence.InviteRepository;
import com.mycard.security.infrastructure.persistence.PendingInviteEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
public class InvitationService {

    private final InviteRepository repository;
    private final IClientInviteEventPublisher publisher;
    private final JwtService jwtService;

    @Inject
    public InvitationService(InviteRepository repository, IClientInviteEventPublisher publisher, JwtService jwtService) {
        this.repository = repository;
        this.publisher = publisher;
        this.jwtService = jwtService;
    }

    public UUID inviteClient(InviteClientCommand command) {
        if (repository.existsActiveInvite(command.getEmail(), command.getTrainerId())) {
            throw new IllegalArgumentException("Client already invited and not yet accepted.");
        }

        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(jwtService.getExpirationDuration().getSeconds());

        String token = jwtService.generateInviteToken(command.getTrainerId(), command.getEmail());

        PendingInviteEntity entity = new PendingInviteEntity(
                command.getTrainerId(),
                command.getEmail(),
                token,
                now,
                expiresAt,
                false
        );

        repository.persist(entity);
        repository.flush();

        UUID inviteId = entity.getId();

        publisher.publish(new ClientInvitedEvent(inviteId, command.getTrainerId(), command.getEmail(), token));

        return inviteId;
    }
}
