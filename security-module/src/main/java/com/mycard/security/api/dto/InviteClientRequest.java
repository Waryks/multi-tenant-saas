package com.mycard.security.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class InviteClientRequest {

    @NotNull
    private UUID trainerId;

    @Email
    @NotBlank
    private String email;
}
