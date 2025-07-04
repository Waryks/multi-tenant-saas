package com.mycard.organization.infrastructure.persistence.mapper;

import com.mycard.organization.domain.model.Organization;
import com.mycard.organization.infrastructure.persistence.OrganizationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi", uses = OrganizationProfileMapper.class)
public interface OrganizationMapper {

    @Mapping(target = "profile", source = "profile")
    OrganizationEntity toEntity(Organization org);

    @Mapping(target = "profile", source = "profile")
    Organization toDomain(OrganizationEntity entity);
}