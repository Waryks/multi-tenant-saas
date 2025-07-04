package com.mycard.client.application.event;

import com.mycard.client.domain.event.WorkoutAssignedEvent;

public interface IWorkoutEventPublisher {
    void publish(WorkoutAssignedEvent event);
}
