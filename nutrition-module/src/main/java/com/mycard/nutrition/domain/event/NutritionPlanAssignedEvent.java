package com.mycard.nutrition.domain.event;

import lombok.Getter;
import messaging.DomainEvent;

import java.time.Instant;
import java.util.UUID;

@Getter
public class NutritionPlanAssignedEvent implements DomainEvent {

    private final UUID planId;
    private final UUID clientId;
    private final Instant occurredAt;

    public NutritionPlanAssignedEvent(UUID planId, UUID clientId) {
        this.planId = planId;
        this.clientId = clientId;
        this.occurredAt = Instant.now();
    }

    @Override
    public Instant occurredAt() {
        return occurredAt;
    }

    @Override
    public String eventName() {
        return "nutrition.assigned";
    }
}
