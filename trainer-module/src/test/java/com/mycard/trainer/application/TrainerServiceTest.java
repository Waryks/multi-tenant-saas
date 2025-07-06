package com.mycard.trainer.application;

import com.mycard.trainer.application.command.CreateTrainerCommand;
import com.mycard.trainer.application.event.ITrainerEventPublisher;
import com.mycard.trainer.application.service.TrainerService;
import com.mycard.trainer.domain.model.Trainer;
import com.mycard.trainer.domain.model.TrainerProfile;
import com.mycard.trainer.infrastructure.persistence.TrainerEntity;
import com.mycard.trainer.infrastructure.persistence.TrainerRepository;
import com.mycard.trainer.infrastructure.persistence.mapper.TrainerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerServiceTest {

    private TrainerRepository repository;
    private ITrainerEventPublisher eventPublisher;
    private TrainerMapper mapper;

    private TrainerService service;

    @BeforeEach
    void setUp() {
        repository = mock(TrainerRepository.class);
        eventPublisher = mock(ITrainerEventPublisher.class);
        mapper = mock(TrainerMapper.class);
        service = new TrainerService(repository, eventPublisher, mapper);
    }

    @Test
    void shouldCreateTrainerAndPublishEvent() {
        // Arrange
        UUID orgId = UUID.randomUUID();
        CreateTrainerCommand command = new CreateTrainerCommand(
                "Alex Trainer",
                "alex@trainer.com",
                orgId,
                "Bio",
                "123456789",
                "Cardio",
                "@alexfit",
                "pic.jpg"
        );

        Trainer trainer = new Trainer(command.getFullName(), command.getEmail(),
                new TrainerProfile(command.getBio(), command.getPhoneNumber(),
                        command.getSpecialization(), command.getInstagramHandle(),
                        command.getProfilePictureUrl()),
                orgId);

        TrainerEntity trainerEntity = new TrainerEntity();
        when(mapper.toEntity(any(Trainer.class))).thenReturn(trainerEntity);

        // Act
        Trainer result = service.createTrainer(command);

        // Assert
        assertNotNull(result);
        assertEquals(result.getOrganizationId(), trainer.getOrganizationId());
        verify(mapper).toEntity(any(Trainer.class));
        verify(repository).persist(trainerEntity);
        verify(eventPublisher).publish(any());
    }

    @Test
    void shouldReturnTrainerById() {
        UUID id = UUID.randomUUID();
        TrainerEntity entity = new TrainerEntity();
        Trainer trainer = mock(Trainer.class);

        when(repository.findByIdOptional(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(trainer);

        Optional<Trainer> result = service.findById(id);

        assertTrue(result.isPresent());
        assertEquals(trainer, result.get());
    }

    @Test
    void shouldReturnEmptyIfTrainerNotFound() {
        UUID id = UUID.randomUUID();
        when(repository.findByIdOptional(id)).thenReturn(Optional.empty());

        Optional<Trainer> result = service.findById(id);

        assertTrue(result.isEmpty());
        verify(mapper, never()).toDomain(any());
    }
}
