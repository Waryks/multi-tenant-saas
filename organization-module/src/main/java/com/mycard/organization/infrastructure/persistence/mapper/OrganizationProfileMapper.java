package com.mycard.organization.infrastructure.persistence.mapper;

import com.mycard.organization.domain.model.OrganizationProfile;
import com.mycard.organization.infrastructure.persistence.EmbeddedOrganizationProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface OrganizationProfileMapper {
    EmbeddedOrganizationProfile toEmbedded(OrganizationProfile profile);
    OrganizationProfile toDomain(EmbeddedOrganizationProfile embedded);
}
