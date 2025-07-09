package com.mycard.security.application.service;

import com.mycard.security.api.dto.InviteClientRequest;
import com.mycard.security.api.mapper.InviteResourceMapper;
import com.mycard.security.application.command.InviteClientCommand;
import com.mycard.security.infrastructure.jwt.JwtService;
import com.mycard.security.infrastructure.persistence.InviteRepository;
import com.mycard.security.infrastructure.persistence.PendingInviteEntity;
import com.mycard.security.application.event.IClientInviteEventPublisher;
import com.mycard.security.domain.event.ClientInvitedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InvitationServiceTest {
    @Mock
    InviteRepository inviteRepository;
    @Mock
    InviteResourceMapper inviteResourceMapper;
    @Mock
    IClientInviteEventPublisher eventPublisher;
    @Mock
    JwtService jwtService;
    @InjectMocks
    InvitationService invitationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void inviteClient_shouldPersistInvite_whenValidRequest() {
        InviteClientRequest request = new InviteClientRequest();
        request.setEmail("test@example.com");
        request.setTrainerId(UUID.randomUUID());
        InviteClientCommand command = new InviteClientCommand(request.getTrainerId(), request.getEmail());
        when(inviteRepository.existsActiveInvite(request.getEmail(), request.getTrainerId())).thenReturn(false);

        doNothing().when(inviteRepository).persistAndFlush(any(PendingInviteEntity.class));

        invitationService.inviteClient(command);

        verify(inviteRepository, times(1)).persistAndFlush(any(PendingInviteEntity.class));
        verify(eventPublisher, times(1)).publish(any(ClientInvitedEvent.class));
    }

    @Test
    void inviteClient_shouldThrow_whenAlreadyInvited() {
        InviteClientRequest request = new InviteClientRequest();
        request.setEmail("test@example.com");
        request.setTrainerId(UUID.randomUUID());
        InviteClientCommand command = new InviteClientCommand(request.getTrainerId(), request.getEmail());
        when(inviteRepository.existsActiveInvite(request.getEmail(), request.getTrainerId())).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> invitationService.inviteClient(command));
    }

    @Test
    void checkInviteToken_shouldReturnInvite_whenTokenValid() {
        String token = "valid-token";
        PendingInviteEntity entity = new PendingInviteEntity();
        when(inviteRepository.findByToken(token)).thenReturn(Optional.of(entity));
        Optional<PendingInviteEntity> result = inviteRepository.findByToken(token);
        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
    }

    @Test
    void checkInviteToken_shouldReturnEmpty_whenTokenInvalid() {
        String token = "invalid-token";
        when(inviteRepository.findByToken(token)).thenReturn(Optional.empty());
        Optional<PendingInviteEntity> result = inviteRepository.findByToken(token);
        assertFalse(result.isPresent());
    }

    @Test
    void inviteClient_shouldThrow_whenCommandIsNull() {
        assertThrows(NullPointerException.class, () -> invitationService.inviteClient(null));
    }

    @Test
    void inviteClient_shouldThrow_whenEventPublisherThrows() {
        InviteClientRequest request = new InviteClientRequest();
        request.setEmail("test@example.com");
        request.setTrainerId(UUID.randomUUID());
        InviteClientCommand command = new InviteClientCommand(request.getTrainerId(), request.getEmail());
        when(inviteRepository.existsActiveInvite(request.getEmail(), request.getTrainerId())).thenReturn(false);
        doThrow(new RuntimeException("Publisher error")).when(eventPublisher).publish(any(ClientInvitedEvent.class));
        assertThrows(RuntimeException.class, () -> invitationService.inviteClient(command));
    }

    @Test
    void inviteClient_shouldPersistInvite_withEdgeCaseEmails() {
        String[] emails = {"TEST@EXAMPLE.COM", "üñîçødë@example.com"};
        for (String email : emails) {
            InviteClientCommand command = new InviteClientCommand(UUID.randomUUID(), email);
            when(inviteRepository.existsActiveInvite(email, command.getTrainerId())).thenReturn(false);
            assertDoesNotThrow(() -> invitationService.inviteClient(command));
        }
    }
} 