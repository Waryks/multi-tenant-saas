package com.mycard.workout.application.event;

import com.mycard.workout.domain.event.WorkoutAssignedEvent;

public interface IWorkoutEventPublisher {
    void publish(WorkoutAssignedEvent event);
}
