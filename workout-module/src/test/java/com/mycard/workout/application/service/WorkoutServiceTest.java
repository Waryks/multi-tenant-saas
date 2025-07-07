package com.mycard.workout.application.service;

import com.mycard.workout.application.command.AssignWorkoutCommand;
import com.mycard.workout.application.event.IWorkoutEventPublisher;
import com.mycard.workout.domain.event.WorkoutAssignedEvent;
import com.mycard.workout.domain.model.Workout;
import com.mycard.workout.infrastructure.persistence.WorkoutEntity;
import com.mycard.workout.infrastructure.persistence.WorkoutRepository;
import com.mycard.workout.infrastructure.persistence.mapper.WorkoutMapper;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkoutServiceTest {
    private WorkoutRepository repository;
    private WorkoutMapper mapper;
    private IWorkoutEventPublisher eventPublisher;
    private WorkoutService service;

    @BeforeEach
    void setUp() {
        repository = mock(WorkoutRepository.class);
        mapper = mock(WorkoutMapper.class);
        eventPublisher = mock(IWorkoutEventPublisher.class);
        service = new WorkoutService(repository, mapper, eventPublisher);
    }

    @Test
    void assignWorkout_shouldPersistAndPublishEvent() {
        UUID trainerId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        AssignWorkoutCommand command = new AssignWorkoutCommand(
                trainerId, clientId, "Test", "Desc", LocalDate.now(), List.of()
        );
        WorkoutEntity entity = mock(WorkoutEntity.class);
        UUID generatedId = UUID.randomUUID();
        when(mapper.toEntity(any(Workout.class))).thenReturn(entity);
        doAnswer(invocation -> {
            when(entity.getId()).thenReturn(generatedId);
            when(entity.getClientId()).thenReturn(clientId);
            return null;
        }).when(repository).persistAndFlush(entity);

        UUID id = service.assignWorkout(command);

        verify(mapper).toEntity(any(Workout.class));
        verify(repository).persistAndFlush(entity);
        verify(eventPublisher).publish(any(WorkoutAssignedEvent.class));
        assertNotNull(id);
        assertEquals(generatedId, id);
    }

    @Test
    void findById_shouldReturnWorkout_whenFound() {
        UUID id = UUID.randomUUID();
        WorkoutEntity entity = new WorkoutEntity();
        Workout workout = mock(Workout.class);
        when(repository.findById(id)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(workout);

        Optional<Workout> result = service.findById(id);
        assertTrue(result.isPresent());
        assertEquals(workout, result.get());
    }

    @Test
    void findById_shouldReturnEmpty_whenNotFound() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(null);
        Optional<Workout> result = service.findById(id);
        assertTrue(result.isEmpty());
    }

    @Test
    void findAllByClientId_shouldReturnMultipleWorkouts() {
        UUID clientId = UUID.randomUUID();
        WorkoutEntity entity1 = new WorkoutEntity();
        WorkoutEntity entity2 = new WorkoutEntity();
        Workout workout1 = mock(Workout.class);
        Workout workout2 = mock(Workout.class);
        PanacheQuery<WorkoutEntity> query = mock(PanacheQuery.class);
        when(repository.find("clientId", clientId)).thenReturn(query);
        when(query.stream()).thenReturn(Stream.of(entity1, entity2));
        when(mapper.toDomain(entity1)).thenReturn(workout1);
        when(mapper.toDomain(entity2)).thenReturn(workout2);

        List<Workout> result = service.findAllByClientId(clientId);
        assertEquals(2, result.size());
        assertTrue(result.contains(workout1));
        assertTrue(result.contains(workout2));
    }

    @Test
    void findAllByClientId_shouldReturnEmptyList() {
        UUID clientId = UUID.randomUUID();
        PanacheQuery<WorkoutEntity> query = mock(PanacheQuery.class);
        when(repository.find("clientId", clientId)).thenReturn(query);
        when(query.list()).thenReturn(Collections.emptyList());
        List<Workout> result = service.findAllByClientId(clientId);
        assertTrue(result.isEmpty());
    }
} 