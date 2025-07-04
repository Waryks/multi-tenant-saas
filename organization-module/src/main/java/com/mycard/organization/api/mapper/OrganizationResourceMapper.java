package com.mycard.organization.api.mapper;

import com.mycard.organization.api.dto.CreateOrganizationRequest;
import com.mycard.organization.api.dto.OrganizationDTO;
import com.mycard.organization.application.command.CreateOrganizationCommand;
import com.mycard.organization.domain.model.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi", uses = OrganizationProfileMapper.class)
public interface OrganizationResourceMapper {

    @Mapping(target = "phoneNumber", source = "profile.phoneNumber")
    @Mapping(target = "address", source = "profile.address")
    @Mapping(target = "logoUrl", source = "profile.logoUrl")
    @Mapping(target = "website", source = "profile.website")
    @Mapping(target = "description", source = "profile.description")
    OrganizationDTO toDto(Organization organization);

    CreateOrganizationCommand toCommand(CreateOrganizationRequest request);
}
