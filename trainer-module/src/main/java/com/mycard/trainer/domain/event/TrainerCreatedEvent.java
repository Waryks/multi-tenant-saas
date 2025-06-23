package com.mycard.trainer.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import messaging.DomainEvent;

import java.time.Instant;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class TrainerCreatedEvent implements DomainEvent {

    private UUID trainerId;
    private UUID organizationId;
    private String email;

    private final Instant occurredAt = Instant.now();

    @Override
    public Instant occurredAt() {
        return occurredAt;
    }

    @Override
    public String eventName() {
        return "trainer.created";
    }
}
