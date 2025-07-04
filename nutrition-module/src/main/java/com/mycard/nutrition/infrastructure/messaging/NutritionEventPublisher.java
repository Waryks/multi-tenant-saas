package com.mycard.nutrition.infrastructure.messaging;

import com.mycard.nutrition.application.event.INutritionEventPublisher;
import com.mycard.nutrition.domain.event.NutritionPlanAssignedEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class NutritionEventPublisher implements INutritionEventPublisher {

    @Inject
    @Channel("nutrition-events")
    Emitter<NutritionPlanAssignedEvent> emitter;

    @Override
    public void publish(NutritionPlanAssignedEvent event) {
        emitter.send(event);
    }
}
