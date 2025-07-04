package com.mycard.nutrition.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Meal {
    private String name;
    private MealTime timeOfDay;
    private String description;
    private int calories;
    private int proteinGrams;
    private int carbsGrams;
    private int fatGrams;
}
