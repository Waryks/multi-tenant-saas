package com.mycard.organization.api.mapper;

import com.mycard.organization.api.dto.OrganizationDTO;
import com.mycard.organization.domain.model.OrganizationProfile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface OrganizationProfileMapper {

    void mapToDto(OrganizationProfile profile, @MappingTarget OrganizationDTO dto);
}
