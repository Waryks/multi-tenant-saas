package com.mycard.client.api.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseDTO {

    @NotBlank
    private String name;

    @PositiveOrZero
    private int sets;

    @PositiveOrZero
    private int reps;

    @PositiveOrZero
    private int restSeconds;

    private String notes;
}
