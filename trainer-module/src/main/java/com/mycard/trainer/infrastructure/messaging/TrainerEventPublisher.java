package com.mycard.trainer.infrastructure.messaging;

import com.mycard.trainer.application.event.ITrainerEventPublisher;
import com.mycard.trainer.domain.event.TrainerCreatedEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class TrainerEventPublisher implements ITrainerEventPublisher {

    @Inject
    @Channel("trainer-events")
    Emitter<TrainerCreatedEvent> emitter;

    @Override
    public void publish(TrainerCreatedEvent event) {
        emitter.send(event);
    }
}