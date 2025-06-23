package com.mycard.trainer.infrastructure.persistence;

import com.mycard.trainer.domain.model.Trainer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface TrainerMapper {
    TrainerEntity toEntity(Trainer trainer);
    Trainer toDomain(TrainerEntity entity);
}
