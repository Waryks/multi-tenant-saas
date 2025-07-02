package com.mycard.client.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientProfileDto {

    @Min(1)
    private int age;

    @Min(0)
    private double heightCm;

    @Min(0)
    private double weightKg;

    @NotBlank
    private String goalDescription;
}
