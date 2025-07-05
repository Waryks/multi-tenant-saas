package com.mycard.security.infrastructure.persistence;

import base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "pending_invites")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PendingInviteEntity extends BaseEntity {

    @Column(nullable = false)
    private UUID trainerId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant expiresAt;

    @Column(nullable = false)
    private boolean accepted;
}