package com.mycard.security.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class JwtInviteClaims {
    private UUID trainerId;
    private String email;
}
