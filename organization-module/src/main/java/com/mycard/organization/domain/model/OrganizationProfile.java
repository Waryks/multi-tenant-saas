package com.mycard.organization.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationProfile {
    private String phoneNumber;
    private String address;
    private String logoUrl;
    private String website;
    private String description;
}