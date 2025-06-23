package com.mycard.trainer.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class TrainerDTO {
    private UUID id;
    private String fullName;
    private String email;
    private String bio;
    private String specialization;
    private String instagramHandle;
}
