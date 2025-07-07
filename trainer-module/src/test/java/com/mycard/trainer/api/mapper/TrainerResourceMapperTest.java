package com.mycard.trainer.api.mapper;

import com.mycard.trainer.api.dto.TrainerDTO;
import com.mycard.trainer.domain.model.Trainer;
import com.mycard.trainer.domain.model.TrainerProfile;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TrainerResourceMapperTest {
    private final TrainerResourceMapper mapper = Mappers.getMapper(TrainerResourceMapper.class);

    @Test
    void shouldMapAllProfileFieldsToDto() {
    
        TrainerProfile profile = new TrainerProfile(
                "Bio text",
                "123456789",
                "Specialization",
                "@insta",
                "picUrl.jpg"
        );
        Trainer trainer = new Trainer(
                UUID.randomUUID(),
                "Full Name",
                "email@example.com",
                profile,
                UUID.randomUUID()
        );

        TrainerDTO dto = mapper.toDto(trainer);
        assertEquals("Bio text", dto.getBio());
        assertEquals("123456789", dto.getPhoneNumber());
        assertEquals("Specialization", dto.getSpecialization());
        assertEquals("@insta", dto.getInstagramHandle());
        assertEquals("picUrl.jpg", dto.getProfilePictureUrl());
    }
} 