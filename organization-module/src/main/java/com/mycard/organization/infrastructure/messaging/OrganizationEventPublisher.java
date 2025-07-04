package com.mycard.organization.infrastructure.messaging;

import com.mycard.organization.application.event.IOrganizationEventPublisher;
import com.mycard.organization.domain.event.OrganizationCreatedEvent;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrganizationEventPublisher implements IOrganizationEventPublisher {
    @Override
    public void publish(OrganizationCreatedEvent event) {
        // No-op for now — future RabbitMQ integration point
    }
}
