package com.mycard.trainer.application.event;

import com.mycard.trainer.domain.event.TrainerCreatedEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import messaging.EventBus;

@ApplicationScoped
public class TrainerEventPublisher {

    @Inject
    EventBus eventBus;

    public void publish(TrainerCreatedEvent event) {
        eventBus.publish("trainer-events", event);
    }
}
