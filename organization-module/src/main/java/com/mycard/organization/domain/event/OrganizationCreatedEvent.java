package com.mycard.organization.domain.event;

import lombok.Getter;
import messaging.DomainEvent;

import java.time.Instant;
import java.util.UUID;

@Getter
public class OrganizationCreatedEvent implements DomainEvent {

    private final UUID organizationId;
    private final String name;
    private final Instant occurredAt;

    public OrganizationCreatedEvent(UUID organizationId, String name) {
        this.organizationId = organizationId;
        this.name = name;
        this.occurredAt = Instant.now();
    }

    @Override
    public Instant occurredAt() {
        return occurredAt;
    }

    @Override
    public String eventName() {
        return "organization.created";

    }
}