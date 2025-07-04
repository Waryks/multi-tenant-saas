package com.mycard.organization.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrganizationRequest {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String contactEmail;

    private String phoneNumber;
    private String address;
    private String logoUrl;
    private String website;
    private String description;
}
