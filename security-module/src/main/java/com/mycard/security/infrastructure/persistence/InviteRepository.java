package com.mycard.security.infrastructure.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class InviteRepository implements PanacheRepositoryBase<PendingInviteEntity, UUID> {

    public Optional<PendingInviteEntity> findByToken(String token) {
        return find("token", token).firstResultOptional();
    }

    public boolean existsActiveInvite(String email, UUID trainerId) {
        return count("email = ?1 and trainerId = ?2 and accepted = false", email, trainerId) > 0;
    }
}
