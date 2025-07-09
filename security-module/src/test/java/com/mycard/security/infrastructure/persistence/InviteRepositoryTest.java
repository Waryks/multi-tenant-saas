package com.mycard.security.infrastructure.persistence;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@QuarkusTest
class InviteRepositoryTest {
    @Inject
    InviteRepository inviteRepository;

    private PendingInviteEntity createEntity(String email, UUID trainerId, String token, boolean accepted) {
        return new PendingInviteEntity(
            trainerId,
            email,
            token,
            Instant.now(),
            Instant.now().plusSeconds(3600),
            accepted
        );
    }

    @Test
    @Transactional
    void shouldPersistAndRetrievePendingInviteEntity() {
        String email = "test@example.com";
        UUID trainerId = UUID.randomUUID();
        String token = UUID.randomUUID().toString();
        PendingInviteEntity entity = createEntity(email, trainerId, token, false);
        inviteRepository.persist(entity);
        inviteRepository.flush();

        Optional<PendingInviteEntity> found = inviteRepository.findByToken(token);
        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals(email, found.get().getEmail());
        Assertions.assertEquals(trainerId, found.get().getTrainerId());
        Assertions.assertFalse(found.get().isAccepted());
    }

    @Test
    @Transactional
    void existsActiveInvite_shouldReturnTrueWhenActiveInviteExists() {
        String email = "active@example.com";
        UUID trainerId = UUID.randomUUID();
        String token = UUID.randomUUID().toString();
        PendingInviteEntity entity = createEntity(email, trainerId, token, false);
        inviteRepository.persist(entity);
        inviteRepository.flush();

        boolean exists = inviteRepository.existsActiveInvite(email, trainerId);
        Assertions.assertTrue(exists);
    }

    @Test
    @Transactional
    void existsActiveInvite_shouldReturnFalseWhenNoActiveInvite() {
        String email = "inactive@example.com";
        UUID trainerId = UUID.randomUUID();
        String token = UUID.randomUUID().toString();
        PendingInviteEntity entity = createEntity(email, trainerId, token, true);
        inviteRepository.persist(entity);
        inviteRepository.flush();

        boolean exists = inviteRepository.existsActiveInvite(email, trainerId);
        Assertions.assertFalse(exists);
    }

    @Test
    @Transactional
    void onlyOneActiveInvitePerEmailTrainerId() {
        String email = "multi@example.com";
        UUID trainerId = UUID.randomUUID();
        String token1 = UUID.randomUUID().toString();
        String token2 = UUID.randomUUID().toString();
        PendingInviteEntity entity1 = createEntity(email, trainerId, token1, false);
        PendingInviteEntity entity2 = createEntity(email, trainerId, token2, true);
        inviteRepository.persist(entity1);
        inviteRepository.persist(entity2);
        inviteRepository.flush();
        boolean exists = inviteRepository.existsActiveInvite(email, trainerId);
        Assertions.assertTrue(exists);
        // Now mark entity1 as accepted and check again
        entity1.setAccepted(true);
        inviteRepository.persist(entity1);
        inviteRepository.flush();
        boolean existsAfter = inviteRepository.existsActiveInvite(email, trainerId);
        Assertions.assertFalse(existsAfter);
    }

    @Test
    @Transactional
    void shouldEnforceUniqueTokenConstraint() {
        String email1 = "unique1@example.com";
        String email2 = "unique2@example.com";
        UUID trainerId1 = UUID.randomUUID();
        UUID trainerId2 = UUID.randomUUID();
        String token = UUID.randomUUID().toString();
        PendingInviteEntity entity1 = createEntity(email1, trainerId1, token, false);
        PendingInviteEntity entity2 = createEntity(email2, trainerId2, token, false);
        inviteRepository.persist(entity1);
        inviteRepository.flush();
        Assertions.assertThrows(Exception.class, () -> {
            inviteRepository.persist(entity2);
            inviteRepository.flush();
        });
    }
} 