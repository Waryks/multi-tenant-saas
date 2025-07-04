package com.mycard.client.infrastructure.persistence.mapper;

import com.mycard.client.domain.model.Client;
import com.mycard.client.infrastructure.persistence.ClientEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi", uses = EmbeddedClientProfileMapper.class)
public interface ClientMapper {
    ClientEntity toEntity(Client client);
    Client toDomain(ClientEntity entity);
}

