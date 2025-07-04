package com.mycard.client.application.service;

import com.mycard.client.application.command.AssignWorkoutCommand;
import com.mycard.client.application.event.IWorkoutEventPublisher;
import com.mycard.client.domain.event.WorkoutAssignedEvent;
import com.mycard.client.domain.model.Workout;
import com.mycard.client.infrastructure.persistence.WorkoutEntity;
import com.mycard.client.infrastructure.persistence.WorkoutRepository;
import com.mycard.client.infrastructure.persistence.mapper.WorkoutMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class WorkoutService {

    private static final Logger LOG = Logger.getLogger(WorkoutService.class);

    private final WorkoutRepository repository;
    private final WorkoutMapper mapper;
    private final IWorkoutEventPublisher eventPublisher;

    @Inject
    public WorkoutService(WorkoutRepository repository, WorkoutMapper mapper, IWorkoutEventPublisher eventPublisher) {
        this.repository = repository;
        this.mapper = mapper;
        this.eventPublisher = eventPublisher;
    }

    public UUID assignWorkout(AssignWorkoutCommand command) {
        LOG.infof("Assigning new workout to client: %s", command.getClientId());

        Workout workout = new Workout(
                UUID.randomUUID(),
                command.getTrainerId(),
                command.getClientId(),
                command.getTitle(),
                command.getDescription(),
                command.getScheduledDate(),
                command.getExercises()
        );

        WorkoutEntity entity = mapper.toEntity(workout);
        repository.persist(entity);

        eventPublisher.publish(new WorkoutAssignedEvent(workout.getId(), workout.getClientId()));

        return workout.getId();
    }

    public Optional<Workout> findById(UUID id) {
        return Optional.ofNullable(repository.findById(id)).map(mapper::toDomain);
    }

    public List<Workout> findAllByClientId(UUID clientId) {
        return repository.find("clientId", clientId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
