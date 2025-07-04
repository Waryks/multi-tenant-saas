package com.mycard.client.infrastructure.persistence.mapper;


import com.mycard.client.domain.model.Workout;
import com.mycard.client.infrastructure.persistence.WorkoutEntity;
import org.mapstruct.*;

@Mapper(componentModel = "cdi", uses = ExerciseMapper.class)
public interface WorkoutMapper {

    WorkoutEntity toEntity(Workout workout);

    Workout toDomain(WorkoutEntity entity);
}
