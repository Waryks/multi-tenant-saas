package com.mycard.nutrition.infrastructure.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class NutritionRepository implements PanacheRepositoryBase<NutritionPlanEntity, UUID> {
}
