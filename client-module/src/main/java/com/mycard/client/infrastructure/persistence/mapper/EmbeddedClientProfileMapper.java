package com.mycard.client.infrastructure.persistence.mapper;

import com.mycard.client.domain.model.ClientProfile;
import com.mycard.client.infrastructure.persistence.EmbeddedClientProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface EmbeddedClientProfileMapper {
    ClientProfile toDomain(EmbeddedClientProfile embedded);
    EmbeddedClientProfile toEmbedded(ClientProfile profile);
}
