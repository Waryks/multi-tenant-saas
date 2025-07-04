package com.mycard.organization.application.event;

import com.mycard.organization.domain.event.OrganizationCreatedEvent;

public interface IOrganizationEventPublisher {
    void publish(OrganizationCreatedEvent event);
}
