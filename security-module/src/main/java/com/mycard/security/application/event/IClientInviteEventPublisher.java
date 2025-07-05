package com.mycard.security.application.event;

import com.mycard.security.domain.event.ClientInvitedEvent;

public interface IClientInviteEventPublisher {
    void publish(ClientInvitedEvent event);
}
