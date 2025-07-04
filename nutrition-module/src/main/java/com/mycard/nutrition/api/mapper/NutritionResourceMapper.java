package com.mycard.nutrition.api.mapper;

import com.mycard.nutrition.api.dto.AssignNutritionPlanRequest;
import com.mycard.nutrition.api.dto.NutritionPlanDTO;
import com.mycard.nutrition.application.command.AssignNutritionPlanCommand;
import com.mycard.nutrition.domain.model.NutritionPlan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi", uses = MealDtoMapper.class)
public interface NutritionResourceMapper {

    @Mapping(target = "meals", source = "meals")
    AssignNutritionPlanCommand toCommand(AssignNutritionPlanRequest request);

    @Mapping(target = "meals", source = "meals")
    NutritionPlanDTO toDto(NutritionPlan plan);
}
