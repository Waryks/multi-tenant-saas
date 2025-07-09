package com.mycard.security.infrastructure.jwt;

import com.mycard.security.domain.model.JwtInviteClaims;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class JwtServiceTest {
    @Inject
    JwtService jwtService;;

    @Test
    void generateAndParseInviteToken_shouldContainCorrectClaims() throws Exception {
        UUID trainerId = UUID.randomUUID();
        String email = "test@example.com";
        String token = jwtService.generateInviteToken(trainerId, email);
        assertNotNull(token);
        JwtInviteClaims claims = jwtService.parseInviteToken(token);
        assertEquals(trainerId, claims.getTrainerId());
        assertEquals(email, claims.getEmail());
    }

    @Test
    void parseInviteToken_shouldThrow_whenTokenIsTampered() {
        UUID trainerId = UUID.randomUUID();
        String email = "test@example.com";
        String token = jwtService.generateInviteToken(trainerId, email);
        // Tamper with the token (remove last char)
        String tampered = token.substring(0, token.length() - 1);
        assertThrows(ParseException.class, () -> jwtService.parseInviteToken(tampered));
    }

    @Test
    void getExpirationDuration_shouldMatchConfig() {
        // Default is 24 hours unless overridden in test config
        assertEquals(24 * 60 * 60, jwtService.getExpirationDuration().getSeconds());
    }
} 