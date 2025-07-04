package com.mycard.nutrition.infrastructure.persistence.mapper;


import com.mycard.nutrition.domain.model.NutritionPlan;
import com.mycard.nutrition.infrastructure.persistence.NutritionPlanEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi", uses = MealMapper.class)
public interface NutritionMapper {

    NutritionPlanEntity toEntity(NutritionPlan plan);

    NutritionPlan toDomain(NutritionPlanEntity entity);
}
