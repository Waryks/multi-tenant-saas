package com.mycard.workout.api.mapper;

import com.mycard.workout.api.dto.AssignWorkoutRequest;
import com.mycard.workout.api.dto.ExerciseDTO;
import com.mycard.workout.api.dto.WorkoutDTO;
import com.mycard.workout.application.command.AssignWorkoutCommand;
import com.mycard.workout.domain.model.Exercise;
import com.mycard.workout.domain.model.Workout;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
class WorkoutResourceMapperTest {

    @Inject
    WorkoutResourceMapper mapper;

    @Test
    void toCommand_shouldMapAssignWorkoutRequestToCommand() {
        AssignWorkoutRequest request = new AssignWorkoutRequest();
        UUID trainerId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        request.setTrainerId(trainerId);
        request.setClientId(clientId);
        request.setTitle("Test");
        request.setDescription("Desc");
        request.setScheduledDate(LocalDate.now());
        ExerciseDTO exerciseDTO = new ExerciseDTO();
        exerciseDTO.setName("Pushup");
        exerciseDTO.setSets(3);
        exerciseDTO.setReps(10);
        exerciseDTO.setRestSeconds(30);
        request.setExercises(List.of(exerciseDTO));

        AssignWorkoutCommand mockCommand = new AssignWorkoutCommand(
                trainerId,
                clientId,
                "Test",
                "Desc",
                request.getScheduledDate(),
                List.of(new Exercise("Pushup", 3, 10, 30, null))
        );

        AssignWorkoutCommand command = mapper.toCommand(request);
        assertEquals(trainerId, command.getTrainerId());
        assertEquals(clientId, command.getClientId());
        assertEquals("Test", command.getTitle());
        assertEquals("Desc", command.getDescription());
        assertEquals(request.getScheduledDate(), command.getScheduledDate());
        assertNotNull(command.getExercises());
        assertEquals(1, command.getExercises().size());
        assertEquals("Pushup", command.getExercises().get(0).getName());
    }

    @Test
    void toDto_shouldMapWorkoutToWorkoutDTO() {
        UUID id = UUID.randomUUID();
        UUID trainerId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        LocalDate date = LocalDate.now();
        Exercise exercise = new Exercise("Squat", 4, 12, 60, "");
        Workout workout = new Workout(id, trainerId, clientId, "Legs", "Leg day", date, List.of(exercise));

        WorkoutDTO mockDto = new WorkoutDTO();
        mockDto.setId(id);
        mockDto.setTrainerId(trainerId);
        mockDto.setClientId(clientId);
        mockDto.setTitle("Legs");
        mockDto.setDescription("Leg day");
        mockDto.setScheduledDate(date);
        ExerciseDTO exerciseDTO = new ExerciseDTO();
        exerciseDTO.setName("Squat");
        exerciseDTO.setSets(4);
        exerciseDTO.setReps(12);
        exerciseDTO.setRestSeconds(60);
        mockDto.setExercises(List.of(exerciseDTO));

        WorkoutDTO dto = mapper.toDto(workout);
        assertEquals(id, dto.getId());
        assertEquals(trainerId, dto.getTrainerId());
        assertEquals(clientId, dto.getClientId());
        assertEquals("Legs", dto.getTitle());
        assertEquals("Leg day", dto.getDescription());
        assertEquals(date, dto.getScheduledDate());
        assertNotNull(dto.getExercises());
        assertEquals(1, dto.getExercises().size());
        assertEquals("Squat", dto.getExercises().get(0).getName());
    }
}