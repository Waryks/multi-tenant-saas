package com.mycard.client.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class WorkoutDTO {
    private UUID id;
    private UUID trainerId;
    private UUID clientId;
    private String title;
    private String description;
    private LocalDate scheduledDate;
    private List<ExerciseDTO> exercises;
}
