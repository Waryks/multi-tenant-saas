package com.mycard.security.api.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterClientRequest {

    @NotBlank
    private String fullName;

    @NotNull
    @Min(12)
    private int age;

    @Positive
    private double heightCm;

    @Positive
    private double weightKg;

    private String goalDescription;
}
