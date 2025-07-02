package com.mycard.client.api.mapper;

import com.mycard.client.api.dto.ClientProfileDto;
import com.mycard.client.domain.model.ClientProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface ClientProfileMapper {
    ClientProfileDto toDto(ClientProfile profile);
}
