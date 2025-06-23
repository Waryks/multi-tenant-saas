package com.mycard.trainer.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateTrainerRequest {
    @NotBlank
    private String fullName;

    @Email
    @NotBlank
    private String email;

    @NotNull
    private UUID organizationId;

    private String bio;
    private String phoneNumber;
    private String specialization;
    private String instagramHandle;
    private String profilePictureUrl;
}
