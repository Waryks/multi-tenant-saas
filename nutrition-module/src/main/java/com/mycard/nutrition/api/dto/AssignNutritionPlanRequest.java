package com.mycard.nutrition.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class AssignNutritionPlanRequest {

    @NotNull
    private UUID trainerId;

    @NotNull
    private UUID clientId;

    @NotBlank
    private String title;

    private String notes;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    private List<MealDTO> meals;
}
