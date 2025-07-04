package com.mycard.nutrition.infrastructure.persistence;

import com.mycard.nutrition.domain.model.MealTime;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MealEmbeddable {

    private String name;
    private MealTime timeOfDay;
    private String description;
    private int calories;
    private int proteinGrams;
    private int carbsGrams;
    private int fatGrams;
}
