package com.mycard.client.api.mapper;

import com.mycard.client.api.dto.ClientDTO;
import com.mycard.client.api.dto.RegisterClientRequest;
import com.mycard.client.application.service.RegisterClientCommand;
import com.mycard.client.domain.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "cdi", uses = ClientProfileMapper.class)
public interface ClientResourceMapper {

    @Mappings({
            @Mapping(source = "profile.age", target = "age"),
            @Mapping(source = "profile.heightCm", target = "heightCm"),
            @Mapping(source = "profile.weightKg", target = "weightKg"),
            @Mapping(source = "profile.goalDescription", target = "goalDescription")
    })
    RegisterClientCommand toCommand(RegisterClientRequest request);

    ClientDTO toDto(Client client);
}
