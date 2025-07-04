package com.mycard.nutrition.application.service;

import com.mycard.nutrition.application.command.AssignNutritionPlanCommand;
import com.mycard.nutrition.application.event.INutritionEventPublisher;
import com.mycard.nutrition.domain.event.NutritionPlanAssignedEvent;
import com.mycard.nutrition.domain.model.NutritionPlan;
import com.mycard.nutrition.infrastructure.persistence.NutritionPlanEntity;
import com.mycard.nutrition.infrastructure.persistence.NutritionRepository;
import com.mycard.nutrition.infrastructure.persistence.mapper.NutritionMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class NutritionService {

    private static final Logger LOG = Logger.getLogger(NutritionService.class);

    private final NutritionRepository repository;
    private final NutritionMapper mapper;
    private final INutritionEventPublisher eventPublisher;

    @Inject
    public NutritionService(NutritionRepository repository, NutritionMapper mapper, INutritionEventPublisher eventPublisher) {
        this.repository = repository;
        this.mapper = mapper;
        this.eventPublisher = eventPublisher;
    }

    public UUID assignNutritionPlan(AssignNutritionPlanCommand command) {
        LOG.infof("Assigning new nutrition plan to client: %s", command.getClientId());

        NutritionPlan plan = new NutritionPlan(
                UUID.randomUUID(),
                command.getTrainerId(),
                command.getClientId(),
                command.getTitle(),
                command.getNotes(),
                command.getStartDate(),
                command.getEndDate(),
                command.getMeals()
        );

        NutritionPlanEntity entity = mapper.toEntity(plan);
        repository.persist(entity);

        eventPublisher.publish(new NutritionPlanAssignedEvent(plan.getId(), plan.getClientId()));

        return plan.getId();
    }

    public Optional<NutritionPlan> findById(UUID id) {
        return Optional.ofNullable(repository.findById(id)).map(mapper::toDomain);
    }
}
