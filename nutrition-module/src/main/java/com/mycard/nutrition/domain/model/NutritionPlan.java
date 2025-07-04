package com.mycard.nutrition.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class NutritionPlan {
    private UUID id;
    private UUID trainerId;
    private UUID clientId;
    private String title;
    private String notes;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Meal> meals;
}
