package com.mycard.client.api.mapper;

import com.mycard.client.api.dto.AssignWorkoutRequest;
import com.mycard.client.api.dto.WorkoutDTO;
import com.mycard.client.application.command.AssignWorkoutCommand;
import com.mycard.client.domain.model.Workout;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi", uses = ExerciseDtoMapper.class)
public interface WorkoutResourceMapper {

    AssignWorkoutCommand toCommand(AssignWorkoutRequest request);

    WorkoutDTO toDto(Workout workout);
}