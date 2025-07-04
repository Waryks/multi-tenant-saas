package com.mycard.client.api.mapper;

import com.mycard.client.api.dto.ExerciseDTO;
import com.mycard.client.domain.model.Exercise;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface ExerciseDtoMapper {

    Exercise toDomain(ExerciseDTO dto);

    ExerciseDTO toDto(Exercise model);

    List<Exercise> toDomainList(List<ExerciseDTO> dtoList);

    List<ExerciseDTO> toDtoList(List<Exercise> modelList);
}
