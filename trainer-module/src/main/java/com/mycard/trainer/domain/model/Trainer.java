package com.mycard.trainer.domain.model;

import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Trainer {
    private UUID id;
    private String fullName;
    private String email;
    private TrainerProfile profile;
    private UUID organizationId;
}