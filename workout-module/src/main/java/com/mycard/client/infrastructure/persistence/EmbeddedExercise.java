package com.mycard.client.infrastructure.persistence;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class EmbeddedExercise {
    private String name;
    private int sets;
    private int reps;
    private int restSeconds;
    private String notes;
}
