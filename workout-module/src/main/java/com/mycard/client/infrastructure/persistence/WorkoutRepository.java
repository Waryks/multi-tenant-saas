package com.mycard.client.infrastructure.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class WorkoutRepository implements PanacheRepositoryBase<WorkoutEntity, UUID> {
}