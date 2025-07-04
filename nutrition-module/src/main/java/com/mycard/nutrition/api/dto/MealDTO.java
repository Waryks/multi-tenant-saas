package com.mycard.nutrition.api.dto;

import com.mycard.nutrition.domain.model.MealTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MealDTO {
    private String name;
    private MealTime timeOfDay;
    private String description;
    private int calories;
    private int proteinGrams;
    private int carbsGrams;
    private int fatGrams;
}