package com.mycard.client.infrastructure.persistence.mapper;

import com.mycard.client.domain.model.Exercise;
import com.mycard.client.infrastructure.persistence.EmbeddedExercise;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface ExerciseMapper {

    List<EmbeddedExercise> map(List<Exercise> exercises);

    List<Exercise> mapToDomain(List<EmbeddedExercise> embeddedExercises);

    Exercise map(EmbeddedExercise embeddedExercise);

    EmbeddedExercise map(Exercise exercise);
}
