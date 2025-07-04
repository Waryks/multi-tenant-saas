package com.mycard.client.infrastructure.messaging;

import com.mycard.client.application.event.IWorkoutEventPublisher;
import com.mycard.client.domain.event.WorkoutAssignedEvent;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class WorkoutEventPublisher implements IWorkoutEventPublisher {

    @Inject
    @Channel("workout-events")
    Emitter<WorkoutAssignedEvent> emitter;

    @Override
    public void publish(WorkoutAssignedEvent event) {
        emitter.send(event);
    }
}
