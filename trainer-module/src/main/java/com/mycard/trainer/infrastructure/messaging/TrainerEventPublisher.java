package com.mycard.trainer.infrastructure.messaging;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import messaging.DomainEvent;
import messaging.EventBus;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class TrainerEventPublisher implements EventBus {

    @Inject
    @Channel("trainer-events")
    Emitter<DomainEvent> emitter;

    @Override
    public <T extends DomainEvent> void publish(String topic, T event) {
        emitter.send(event);
    }
}
