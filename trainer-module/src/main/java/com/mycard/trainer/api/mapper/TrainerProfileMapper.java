package com.mycard.trainer.api.mapper;

import com.mycard.trainer.api.dto.TrainerProfileDto;
import com.mycard.trainer.domain.model.TrainerProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface TrainerProfileMapper {
    TrainerProfileDto toDto(TrainerProfile profile);
}