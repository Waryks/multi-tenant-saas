package com.mycard.trainer.api.mapper;

import com.mycard.trainer.api.dto.CreateTrainerRequest;
import com.mycard.trainer.api.dto.TrainerDTO;

import com.mycard.trainer.application.service.CreateTrainerCommand;
import com.mycard.trainer.domain.model.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "cdi", uses = TrainerProfileMapper.class)
public interface TrainerResourceMapper {

    @Mappings({
            @Mapping(source = "profile.bio", target = "bio"),
            @Mapping(source = "profile.phoneNumber", target = "phoneNumber"),
            @Mapping(source = "profile.specialization", target = "specialization"),
            @Mapping(source = "profile.instagramHandle", target = "instagramHandle"),
            @Mapping(source = "profile.profilePictureUrl", target = "profilePictureUrl")
    })
    CreateTrainerCommand toCommand(CreateTrainerRequest request);

    TrainerDTO toDto(Trainer trainer);
}
