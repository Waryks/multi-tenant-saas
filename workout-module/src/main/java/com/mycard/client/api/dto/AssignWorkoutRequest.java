package com.mycard.client.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class AssignWorkoutRequest {
    @NotNull
    private UUID trainerId;

    @NotNull
    private UUID clientId;

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private LocalDate scheduledDate;

    private List<ExerciseDTO> exercises;
}