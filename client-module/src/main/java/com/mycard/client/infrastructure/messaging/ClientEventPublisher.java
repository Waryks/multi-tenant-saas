package com.mycard.client.infrastructure.messaging;

import com.mycard.client.application.event.IClientEventPublisher;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import messaging.DomainEvent;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class ClientEventPublisher implements IClientEventPublisher {

    @Inject
    @Channel("client-events")
    Emitter<DomainEvent> emitter;

    @Override
    public void publish(DomainEvent event) {
        emitter.send(event);
    }
}
