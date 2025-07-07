package com.mycard.workout.application.service;

import com.mycard.workout.application.command.AssignWorkoutCommand;
import com.mycard.workout.application.event.IWorkoutEventPublisher;
import com.mycard.workout.domain.event.WorkoutAssignedEvent;
import com.mycard.workout.domain.model.Workout;
import com.mycard.workout.infrastructure.persistence.WorkoutEntity;
import com.mycard.workout.infrastructure.persistence.WorkoutRepository;
import com.mycard.workout.infrastructure.persistence.mapper.WorkoutMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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

    @Transactional
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
        repository.persistAndFlush(entity);

        eventPublisher.publish(new WorkoutAssignedEvent(entity.getId(), entity.getClientId()));

        return entity.getId();
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
