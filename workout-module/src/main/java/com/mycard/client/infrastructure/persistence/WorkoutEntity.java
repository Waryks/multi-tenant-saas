package com.mycard.client.infrastructure.persistence;

import base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "workouts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutEntity extends BaseEntity {

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private LocalDate scheduledDate;

    @Column(nullable = false)
    private java.util.UUID trainerId;

    @Column(nullable = false)
    private java.util.UUID clientId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "workout_exercises", joinColumns = @JoinColumn(name = "workout_id"))
    private List<EmbeddedExercise> exercises;
}
