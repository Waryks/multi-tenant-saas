package com.mycard.organization.infrastructure.persistence;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmbeddedOrganizationProfile {
    private String phoneNumber;
    private String address;
    private String logoUrl;
    private String website;
    private String description;
}