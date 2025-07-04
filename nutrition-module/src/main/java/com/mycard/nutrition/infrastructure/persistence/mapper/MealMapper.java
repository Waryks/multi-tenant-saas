package com.mycard.nutrition.infrastructure.persistence.mapper;

import com.mycard.nutrition.domain.model.Meal;
import com.mycard.nutrition.infrastructure.persistence.MealEmbeddable;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface MealMapper {

    List<Meal> toDomain(List<MealEmbeddable> embedded);

    List<MealEmbeddable> toEntity(List<Meal> domain);
}
