package com.mycard.organization.infrastructure.persistence;

import base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "organizations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String contactEmail;

    @Embedded
    private EmbeddedOrganizationProfile profile;
}
