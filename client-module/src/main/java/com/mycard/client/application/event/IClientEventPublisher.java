package com.mycard.client.application.event;

import messaging.DomainEvent;

public interface IClientEventPublisher {
    void publish(DomainEvent event);
}