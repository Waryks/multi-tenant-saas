package com.mycard.trainer.infrastructure.persistence;

import com.mycard.trainer.domain.model.TrainerProfile;
import com.mycard.trainer.infrastructure.persistence.mapper.TrainerMapper;
import com.mycard.trainer.domain.model.Trainer;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class TrainerRepositoryTest {

    @Inject
    TrainerRepository repository;

    @Inject
    TrainerMapper mapper;

    @Test
    @Transactional
    void shouldPersistAndRetrieveTrainer() {
        Trainer trainer = new Trainer(
                "Jane Smith",
                "jane@mycard.io",
                new TrainerProfile("Experienced", "555-1234", "Strength", "@janefit", "jane.jpg"),
                UUID.randomUUID()
        );
        TrainerEntity entity = mapper.toEntity(trainer);

        repository.persist(entity);

        Optional<TrainerEntity> found = repository.findByIdOptional(entity.getId());

        assertTrue(found.isPresent());
        assertEquals("Jane Smith", found.get().getFullName());
    }
}
