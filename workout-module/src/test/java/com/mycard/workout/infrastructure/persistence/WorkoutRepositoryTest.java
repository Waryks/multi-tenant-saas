package com.mycard.workout.infrastructure.persistence;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class WorkoutRepositoryTest {
    @Inject
    WorkoutRepository repository;

    @Test
    @Transactional
    void shouldPersistAndRetrieveWorkoutEntity() {
        WorkoutEntity entity = new WorkoutEntity();
        entity.setTitle("Test Workout");
        entity.setDescription("Integration");
        entity.setScheduledDate(LocalDate.now().plusDays(1));
        entity.setTrainerId(UUID.randomUUID());
        entity.setClientId(UUID.randomUUID());
        entity.setExercises(List.of());

        repository.persist(entity);
        UUID id = entity.getId();
        assertNotNull(id);

        WorkoutEntity found = repository.findById(id);
        assertNotNull(found);
        assertEquals("Test Workout", found.getTitle());
        assertEquals(entity.getClientId(), found.getClientId());
    }
} 