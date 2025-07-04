package com.mycard.nutrition.api.mapper;

import com.mycard.nutrition.api.dto.MealDTO;
import com.mycard.nutrition.domain.model.Meal;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface MealDtoMapper {
    Meal toDomain(MealDTO dto);
    MealDTO toDto(Meal meal);

    List<Meal> toDomainList(List<MealDTO> dtos);
    List<MealDTO> toDtoList(List<Meal> meals);
}
