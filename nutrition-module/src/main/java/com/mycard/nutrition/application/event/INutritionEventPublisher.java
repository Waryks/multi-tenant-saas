package com.mycard.nutrition.application.event;

import com.mycard.nutrition.domain.event.NutritionPlanAssignedEvent;

public interface INutritionEventPublisher {
    void publish(NutritionPlanAssignedEvent event);
}
