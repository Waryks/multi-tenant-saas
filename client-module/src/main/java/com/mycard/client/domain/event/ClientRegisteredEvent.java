package com.mycard.client.domain.event;


import lombok.Getter;
import messaging.DomainEvent;

import java.time.Instant;
import java.util.UUID;

@Getter
public class ClientRegisteredEvent implements DomainEvent {

    private final UUID clientId;
    private final UUID trainerId;
    private final Instant occurredAt;

    public ClientRegisteredEvent(UUID clientId, UUID trainerId) {
        this.clientId = clientId;
        this.trainerId = trainerId;
        this.occurredAt = Instant.now();
    }

    @Override
    public Instant occurredAt() {
        return occurredAt;
    }

    @Override
    public String eventName() {
        return "client.registered";
    }
}
