package com.mycard.workout.api.mapper;

import com.mycard.workout.api.dto.ExerciseDTO;
import com.mycard.workout.domain.model.Exercise;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class ExerciseDtoMapperTest {
    private final ExerciseDtoMapper mapper = Mappers.getMapper(ExerciseDtoMapper.class);

    @Test
    void toDto_shouldMapExerciseToExerciseDTO() {
        Exercise exercise = new Exercise("Bench Press", 3, 8, 90, "Chest");
        ExerciseDTO dto = mapper.toDto(exercise);
        assertEquals("Bench Press", dto.getName());
        assertEquals(3, dto.getSets());
        assertEquals(8, dto.getReps());
        assertEquals(90, dto.getRestSeconds());
        assertEquals("Chest", dto.getNotes());
    }

    @Test
    void toDomain_shouldMapExerciseDTOToExercise() {
        ExerciseDTO dto = new ExerciseDTO();
        dto.setName("Deadlift");
        dto.setSets(5);
        dto.setReps(5);
        dto.setRestSeconds(120);
        dto.setNotes("Back");
        Exercise exercise = mapper.toDomain(dto);
        assertEquals("Deadlift", exercise.getName());
        assertEquals(5, exercise.getSets());
        assertEquals(5, exercise.getReps());
        assertEquals(120, exercise.getRestSeconds());
        assertEquals("Back", exercise.getNotes());
    }
} 