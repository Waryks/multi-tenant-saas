package com.mycard.organization.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class Organization {
    private UUID id;
    private String name;
    private String contactEmail;
    private OrganizationProfile profile;
}
