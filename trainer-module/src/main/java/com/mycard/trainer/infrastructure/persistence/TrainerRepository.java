package com.mycard.trainer.infrastructure.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class TrainerRepository implements PanacheRepositoryBase<TrainerEntity, UUID> { }