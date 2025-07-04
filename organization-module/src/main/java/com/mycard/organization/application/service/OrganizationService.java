package com.mycard.organization.application.service;

import com.mycard.organization.application.command.CreateOrganizationCommand;
import com.mycard.organization.application.event.IOrganizationEventPublisher;
import com.mycard.organization.domain.event.OrganizationCreatedEvent;
import com.mycard.organization.domain.model.Organization;
import com.mycard.organization.domain.model.OrganizationProfile;
import com.mycard.organization.infrastructure.persistence.OrganizationEntity;
import com.mycard.organization.infrastructure.persistence.OrganizationRepository;
import com.mycard.organization.infrastructure.persistence.mapper.OrganizationMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class OrganizationService {

    private static final Logger LOG = Logger.getLogger(OrganizationService.class);

    private final OrganizationRepository repository;
    private final OrganizationMapper mapper;
    private final IOrganizationEventPublisher eventPublisher;

    @Inject
    public OrganizationService(OrganizationRepository repository,
                               OrganizationMapper mapper,
                               IOrganizationEventPublisher eventPublisher) {
        this.repository = repository;
        this.mapper = mapper;
        this.eventPublisher = eventPublisher;
    }

    public UUID createOrganization(CreateOrganizationCommand command) {
        LOG.infof("Creating organization: %s", command.getName());

        Organization org = new Organization(
                UUID.randomUUID(),
                command.getName(),
                command.getContactEmail(),
                new OrganizationProfile(
                        command.getPhoneNumber(),
                        command.getAddress(),
                        command.getLogoUrl(),
                        command.getWebsite(),
                        command.getDescription()
                )
        );

        OrganizationEntity entity = mapper.toEntity(org);
        repository.persist(entity);

        eventPublisher.publish(new OrganizationCreatedEvent(org.getId(), org.getName()));

        return org.getId();
    }

    public Optional<Organization> findById(UUID id) {
        return repository.findByIdOptional(id).map(mapper::toDomain);
    }
}
