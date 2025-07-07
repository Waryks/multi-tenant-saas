package com.mycard.workout.application.command;

import com.mycard.workout.domain.model.Exercise;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class AssignWorkoutCommand {
    private UUID trainerId;
    private UUID clientId;
    private String title;
    private String description;
    private LocalDate scheduledDate;
    private List<Exercise> exercises;
}