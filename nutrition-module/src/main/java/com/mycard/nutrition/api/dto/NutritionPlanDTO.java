package com.mycard.nutrition.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class NutritionPlanDTO {
    private UUID id;
    private UUID trainerId;
    private UUID clientId;
    private String title;
    private String notes;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<MealDTO> meals;
}