package com.mycard.client.domain.event;

import lombok.Getter;
import messaging.DomainEvent;

import java.time.Instant;
import java.util.UUID;

@Getter
public class WorkoutAssignedEvent implements DomainEvent {

    private final UUID workoutId;
    private final UUID clientId;
    private final Instant occurredAt;

    public WorkoutAssignedEvent(UUID workoutId, UUID clientId) {
        this.workoutId = workoutId;
        this.clientId = clientId;
        this.occurredAt = Instant.now();
    }

    @Override
    public Instant occurredAt() {
        return occurredAt;
    }

    @Override
    public String eventName() {
        return "workout.assigned";
    }
}