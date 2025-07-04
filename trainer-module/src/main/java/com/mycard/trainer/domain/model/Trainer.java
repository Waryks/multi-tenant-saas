package com.mycard.trainer.domain.model;

import base.BaseEntity;
import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Trainer extends BaseEntity {

    private String fullName;
    private String email;
    private TrainerProfile profile;
    private UUID organizationId;
}