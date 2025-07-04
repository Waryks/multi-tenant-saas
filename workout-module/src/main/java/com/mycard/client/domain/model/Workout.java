package com.mycard.client.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Workout {
    private UUID id;
    private UUID trainerId;
    private UUID clientId;
    private String title;
    private String description;
    private LocalDate scheduledDate;
    private List<Exercise> exercises;
}
