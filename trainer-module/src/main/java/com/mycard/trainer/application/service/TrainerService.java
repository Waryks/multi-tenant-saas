package com.mycard.trainer.application.service;

import com.mycard.trainer.application.event.TrainerEventPublisher;
import com.mycard.trainer.domain.event.TrainerCreatedEvent;
import com.mycard.trainer.domain.model.TrainerProfile;
import com.mycard.trainer.infrastructure.persistence.TrainerRepository;
import com.mycard.trainer.domain.model.Trainer;
import com.mycard.trainer.infrastructure.persistence.TrainerEntity;
import com.mycard.trainer.infrastructure.persistence.TrainerMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class TrainerService {

    private static final Logger LOG = Logger.getLogger(TrainerService.class);

    private final TrainerRepository repository;
    private final TrainerEventPublisher eventPublisher;
    private final TrainerMapper mapper;

    @Inject
    public TrainerService(TrainerRepository repository,
                          TrainerEventPublisher eventPublisher,
                          TrainerMapper mapper) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
        this.mapper = mapper;
    }

    public UUID createTrainer(CreateTrainerCommand command) {
        LOG.infof("Creating trainer for email: %s", command.getEmail());

        TrainerProfile profile = new TrainerProfile(
                command.getBio(),
                command.getPhoneNumber(),
                command.getSpecialization(),
                command.getInstagramHandle(),
                command.getProfilePictureUrl()
        );

        Trainer trainer = new Trainer(
                command.getFullName(),
                command.getEmail(),
                profile,
                command.getOrganizationId()
        );

        TrainerEntity entity = mapper.toEntity(trainer);
        repository.persist(entity);

        LOG.infof("Trainer persisted with ID: %s", trainer.getId());

        TrainerCreatedEvent event = new TrainerCreatedEvent(
                trainer.getId(),
                trainer.getOrganizationId(),
                trainer.getEmail()
        );
        eventPublisher.publish(event);

        LOG.infof("Published TrainerCreatedEvent for ID: %s", trainer.getId());

        return trainer.getId();
    }

    public Optional<Trainer> findById(UUID id) {
        LOG.debugf("Looking up trainer by ID: %s", id);
        return repository.findByIdOptional(id)
                .map(mapper::toDomain);
    }
}
