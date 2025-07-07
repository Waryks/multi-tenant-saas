package com.mycard.workout.api.mapper;

import com.mycard.workout.api.dto.AssignWorkoutRequest;
import com.mycard.workout.api.dto.WorkoutDTO;
import com.mycard.workout.application.command.AssignWorkoutCommand;
import com.mycard.workout.domain.model.Workout;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi", uses = ExerciseDtoMapper.class)
public interface WorkoutResourceMapper {

    AssignWorkoutCommand toCommand(AssignWorkoutRequest request);

    WorkoutDTO toDto(Workout workout);
}