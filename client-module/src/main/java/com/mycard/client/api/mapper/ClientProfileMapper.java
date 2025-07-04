package com.mycard.client.api.mapper;

import com.mycard.client.api.dto.ClientProfileDTO;
import com.mycard.client.domain.model.ClientProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface ClientProfileMapper {
    ClientProfileDTO toDto(ClientProfile profile);
}
