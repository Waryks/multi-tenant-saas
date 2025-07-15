package com.mycard.workout.infrastructure.persistence.mapper;


import com.mycard.workout.domain.model.Workout;
import com.mycard.workout.infrastructure.persistence.WorkoutEntity;
import org.mapstruct.*;

@Mapper(componentModel = "jakarta-cdi", uses = ExerciseMapper.class)
public interface WorkoutMapper {

    WorkoutEntity toEntity(Workout workout);

    Workout toDomain(WorkoutEntity entity);
}
