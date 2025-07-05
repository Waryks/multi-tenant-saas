package com.mycard.security.infrastructure.messaging;

import com.mycard.security.application.event.IClientInviteEventPublisher;
import com.mycard.security.domain.event.ClientInvitedEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class ClientInvitePublisher implements IClientInviteEventPublisher {

    @Inject
    @Channel("client-invite-events")
    Emitter<ClientInvitedEvent> emitter;

    @Override
    public void publish(ClientInvitedEvent event) {
        emitter.send(event);
    }
}
