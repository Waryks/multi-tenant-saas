package com.mycard.security.infrastructure.jwt;

import com.mycard.security.domain.model.JwtInviteClaims;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.auth.principal.JWTParser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class JwtService {

    @Inject
    JWTParser jwtParser;

    @ConfigProperty(name = "mycard.jwt.invite-expiration-hours", defaultValue = "24")
    int expirationHours;

    public String generateInviteToken(UUID trainerId, String email) {
        Instant now = Instant.now();
        Instant expiration = now.plus(Duration.ofHours(expirationHours));

        return Jwt.claims(Map.of(
                        "trainerId", trainerId.toString(),
                        "email", email
                ))
                .issuer("https://mycard.io")
                .audience("client-invite")
                .issuedAt(now)
                .expiresAt(expiration)
                .sign();
    }

    public JwtInviteClaims parseInviteToken(String token) throws ParseException {
        var jwt = jwtParser.parse(token);

        return new JwtInviteClaims(
                UUID.fromString(jwt.getClaim("trainerId")),
                jwt.getClaim("email")
        );
    }

    public Duration getExpirationDuration() {
        return Duration.ofHours(expirationHours);
    }
}
