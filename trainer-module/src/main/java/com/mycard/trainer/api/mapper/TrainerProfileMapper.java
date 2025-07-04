package com.mycard.trainer.api.mapper;

import com.mycard.trainer.api.dto.TrainerProfileDTO;
import com.mycard.trainer.domain.model.TrainerProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface TrainerProfileMapper {
    TrainerProfileDTO toDto(TrainerProfile profile);
}