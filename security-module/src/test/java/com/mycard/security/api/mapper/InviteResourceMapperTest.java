package com.mycard.security.api.mapper;

import com.mycard.security.api.dto.InviteClientRequest;
import com.mycard.security.application.command.InviteClientCommand;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class InviteResourceMapperTest {

    @Inject
    InviteResourceMapper mapper;

    @Test
    void toCommand_shouldMapInviteClientRequestToCommand() {
        UUID trainerId = UUID.randomUUID();
        String email = "test@example.com";
        InviteClientRequest request = new InviteClientRequest();
        request.setTrainerId(trainerId);
        request.setEmail(email);

        InviteClientCommand command = mapper.toCommand(request);
        assertNotNull(command);
        assertEquals(trainerId, command.getTrainerId());
        assertEquals(email, command.getEmail());
    }
} 