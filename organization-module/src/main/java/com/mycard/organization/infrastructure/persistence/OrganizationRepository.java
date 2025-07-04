package com.mycard.organization.infrastructure.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class OrganizationRepository implements PanacheRepositoryBase<OrganizationEntity, UUID> {
}