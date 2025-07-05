package com.mycard.security.domain.event;

import lombok.Getter;
import messaging.DomainEvent;

import java.time.Instant;
import java.util.UUID;

@Getter
public class ClientInvitedEvent implements DomainEvent {

    private final UUID inviteId;
    private final UUID trainerId;
    private final String email;
    private final String token;
    private final Instant occurredAt;

    public ClientInvitedEvent(UUID inviteId, UUID trainerId, String email, String token) {
        this.inviteId = inviteId;
        this.trainerId = trainerId;
        this.email = email;
        this.token = token;
        this.occurredAt = Instant.now();
    }

    @Override
    public Instant occurredAt() {
        return occurredAt;
    }

    @Override
    public String eventName() {
        return "client.invited";
    }
}
