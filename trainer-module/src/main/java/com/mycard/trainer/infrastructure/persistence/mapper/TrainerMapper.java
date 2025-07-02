package com.mycard.trainer.infrastructure.persistence.mapper;

import com.mycard.trainer.domain.model.Trainer;
import com.mycard.trainer.infrastructure.persistence.TrainerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi", uses = EmbeddedTrainerProfileMapper.class)
public interface TrainerMapper {
    TrainerEntity toEntity(Trainer trainer);
    Trainer toDomain(TrainerEntity entity);
}
