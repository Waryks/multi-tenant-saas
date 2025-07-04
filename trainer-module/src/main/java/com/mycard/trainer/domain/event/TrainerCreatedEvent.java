package com.mycard.trainer.domain.event;

import lombok.Getter;
import messaging.DomainEvent;

import java.time.Instant;
import java.util.UUID;

@Getter
public class TrainerCreatedEvent implements DomainEvent {

    private final UUID trainerId;
    private final UUID organizationId;
    private final String email;
    private final Instant occurredAt;

    public TrainerCreatedEvent(UUID trainerId, UUID organizationId, String email) {
        this.trainerId = trainerId;
        this.organizationId = organizationId;
        this.email = email;
        this.occurredAt = Instant.now();
    }
    @Override
    public Instant occurredAt() {
        return occurredAt;
    }

    @Override
    public String eventName() {
        return "trainer.created";
    }
}
