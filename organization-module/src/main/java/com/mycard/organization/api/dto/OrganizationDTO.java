package com.mycard.organization.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrganizationDTO {

    private UUID id;
    private String name;
    private String contactEmail;

    private String phoneNumber;
    private String address;
    private String logoUrl;
    private String website;
    private String description;
}
