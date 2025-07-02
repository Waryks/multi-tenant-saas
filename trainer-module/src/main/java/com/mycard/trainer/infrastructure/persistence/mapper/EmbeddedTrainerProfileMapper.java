package com.mycard.trainer.infrastructure.persistence.mapper;

import com.mycard.trainer.domain.model.TrainerProfile;
import com.mycard.trainer.infrastructure.persistence.EmbeddedTrainerProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface EmbeddedTrainerProfileMapper {
    TrainerProfile toDomain(EmbeddedTrainerProfile embedded);
    EmbeddedTrainerProfile toEmbedded(TrainerProfile profile);
}