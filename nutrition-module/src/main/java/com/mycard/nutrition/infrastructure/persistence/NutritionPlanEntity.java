package com.mycard.nutrition.infrastructure.persistence;

import base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "nutrition_plans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NutritionPlanEntity extends BaseEntity {

    @Column(nullable = false)
    private UUID trainerId;

    @Column(nullable = false)
    private UUID clientId;

    @Column(nullable = false)
    private String title;

    private String notes;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "nutrition_meals", joinColumns = @JoinColumn(name = "plan_id"))
    private List<MealEmbeddable> meals;
}
