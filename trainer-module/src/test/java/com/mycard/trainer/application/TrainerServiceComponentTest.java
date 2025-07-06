package com.mycard.trainer.application;

import com.mycard.trainer.application.command.CreateTrainerCommand;
import com.mycard.trainer.application.service.TrainerService;
import com.mycard.trainer.domain.event.TrainerCreatedEvent;
import com.mycard.trainer.domain.model.Trainer;
import com.mycard.trainer.infrastructure.persistence.TrainerRepository;
import com.mycard.trainer.infrastructure.persistence.mapper.TrainerMapper;
import com.mycard.trainer.application.event.ITrainerEventPublisher;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.InjectMock;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.UUID;

import static io.smallrye.common.constraint.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@QuarkusTest
class TrainerServiceComponentTest {

    @Inject
    TrainerService trainerService;

    @InjectMock
    TrainerRepository repository;

    @InjectMock
    ITrainerEventPublisher eventPublisher;

    @InjectMock
    TrainerMapper mapper;

    @Test
    void shouldCreateTrainerAndPublishEvent() {
        // Arrange
        UUID orgId = UUID.randomUUID();

        var command = new CreateTrainerCommand(
                "John Doe", "john@fit.com", orgId,
                "Bio", "12345", "Strength", "@john", "pic.jpg"
        );

        var trainer = new Trainer(command.getFullName(), command.getEmail(), null, orgId);

        when(mapper.toEntity(any())).thenReturn(null);
        when(mapper.toDomain(any())).thenReturn(trainer);

        // Act
        Trainer result = trainerService.createTrainer(command);

        // Assert
        assertNotNull(result);
        assertEquals("john@fit.com", result.getEmail());

        ArgumentCaptor<TrainerCreatedEvent> eventCaptor = ArgumentCaptor.forClass(TrainerCreatedEvent.class);
        verify(eventPublisher).publish(eventCaptor.capture());

        TrainerCreatedEvent publishedEvent = eventCaptor.getValue();
        assertEquals("john@fit.com", publishedEvent.getEmail());
    }
}
