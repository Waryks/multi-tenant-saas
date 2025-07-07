package com.mycard.workout.infrastructure.messaging;

import com.mycard.workout.domain.event.WorkoutAssignedEvent;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.Mockito.*;

class WorkoutEventPublisherTest {
    private Emitter<WorkoutAssignedEvent> emitter;
    private WorkoutEventPublisher publisher;

    @BeforeEach
    void setUp() {
        emitter = mock(Emitter.class);
        publisher = new WorkoutEventPublisher();
        // Use reflection to inject the mock emitter
        try {
            var field = WorkoutEventPublisher.class.getDeclaredField("emitter");
            field.setAccessible(true);
            field.set(publisher, emitter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void publish_shouldSendEventToEmitter() {
        WorkoutAssignedEvent event = new WorkoutAssignedEvent(UUID.randomUUID(), UUID.randomUUID());
        publisher.publish(event);
        verify(emitter, times(1)).send(event);
    }
} 