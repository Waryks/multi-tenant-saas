package com.mycard.security.api.rest;

import com.mycard.security.acl.client.ClientRegistrationClient;
import com.mycard.security.api.dto.InviteClientRequest;
import com.mycard.security.api.dto.RegisterClientRequest;
import com.mycard.security.infrastructure.persistence.InviteRepository;
import com.mycard.security.infrastructure.persistence.PendingInviteEntity;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class AuthenticationResourceTest {

    @InjectMock
    @RestClient
    ClientRegistrationClient clientRegistrationClient;

    @InjectMock
    InviteRepository inviteRepository;

    @Test
    void inviteClient_shouldReturn201_whenValidRequest() {
        InviteClientRequest request = new InviteClientRequest();
        request.setEmail("test@example.com");
        request.setTrainerId(UUID.randomUUID());
        given()
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .post("/auth/invite-client")
            .then()
            .statusCode(201)
            .header("Location", notNullValue());
    }

    @Test
    void inviteClient_shouldReturn400_whenInvalidRequest() {
        InviteClientRequest request = new InviteClientRequest();
        given()
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .post("/auth/invite-client")
            .then()
            .statusCode(400);
    }

    @Test
    void inviteClient_shouldReturn400_whenInvalidEmail() {
        InviteClientRequest request = new InviteClientRequest();
        request.setEmail("not-an-email");
        request.setTrainerId(UUID.randomUUID());
        given()
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .post("/auth/invite-client")
            .then()
            .statusCode(400);
    }

    @Test
    void inviteClient_shouldReturn400_whenMissingTrainerId() {
        InviteClientRequest request = new InviteClientRequest();
        request.setEmail("test@example.com");
        given()
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .post("/auth/invite-client")
            .then()
            .statusCode(400);
    }

    @Test
    void inviteClient_shouldReturn400_whenMissingEmail() {
        InviteClientRequest request = new InviteClientRequest();
        request.setTrainerId(UUID.randomUUID());
        // email not set
        given()
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .post("/auth/invite-client")
            .then()
            .statusCode(400);
    }

    @Test
    void acceptInvite_shouldReturn200_whenValidTokenAndRequest() {
        var trainerId = UUID.randomUUID();
        var trainerEmail = "test@example.com";

        var now = Instant.now();
        var expiration = now.plus(Duration.ofHours(24));

        String token = Jwt.claims(Map.of(
                        "trainerId", trainerId.toString(),
                        "email", trainerEmail
                ))
                .subject(trainerEmail)
                .issuer("https://mycard.io")
                .audience("client-invite")
                .issuedAt(now)
                .expiresAt(expiration)
                .sign();

        RegisterClientRequest request = new RegisterClientRequest();
        request.setFullName("Test User");
        request.setAge(25);
        request.setHeightCm(180.0);
        request.setWeightKg(75.0);
        request.setGoalDescription("Lose weight");

        org.mockito.Mockito.doNothing().when(clientRegistrationClient).createClientFromInvite(trainerId, trainerEmail, request);
        org.mockito.Mockito.when(inviteRepository.findByToken(token)).thenReturn(Optional.of(new PendingInviteEntity()));

        given()
            .contentType(ContentType.JSON)
            .header("X-Invite-Token", token)
            .body(request)
            .when()
            .post("/auth/accept-invite")
            .then()
            .statusCode(200);
    }

    @Test
    void acceptInvite_shouldReturn400_whenInvalidRequest() {
        String token = Jwt.claims()
                .claim("sub", "user-id-123")
                .claim("email", "test@example.com")
                .issuer("https://mycard.io")
                .audience("client-invite")
                .sign();

        RegisterClientRequest request = new RegisterClientRequest();
        given()
            .contentType(ContentType.JSON)
            .header("X-Invite-Token", token)
            .body(request)
            .when()
            .post("/auth/accept-invite")
            .then()
            .statusCode(400);
    }

    @Test
    void acceptInvite_shouldReturn401_whenMissingOrInvalidToken() {
        RegisterClientRequest request = new RegisterClientRequest();
        request.setFullName("Test User");
        request.setAge(25);
        request.setHeightCm(180.0);
        request.setWeightKg(75.0);
        request.setGoalDescription("Lose weight");
        given()
            .contentType(ContentType.JSON)
            .header("X-Invite-Token", Jwt.claims().sign())
            .body(request)
            .when()
            .post("/auth/accept-invite")
            .then()
            .statusCode(anyOf(is(400), is(401)));
    }

    @Test
    void acceptInvite_shouldReturn400_whenMissingToken() {
        RegisterClientRequest request = new RegisterClientRequest();
        request.setFullName("Test User");
        request.setAge(25);
        request.setHeightCm(180.0);
        request.setWeightKg(75.0);
        request.setGoalDescription("Lose weight");
        given()
            .contentType(ContentType.JSON)
            // no token param
            .body(request)
            .when()
            .post("/auth/accept-invite")
            .then()
            .statusCode(400);
    }

    @Test
    void acceptInvite_shouldReturn401_whenInvalidTokenFormat() {
        RegisterClientRequest request = new RegisterClientRequest();
        request.setFullName("Test User");
        request.setAge(25);
        request.setHeightCm(180.0);
        request.setWeightKg(75.0);
        request.setGoalDescription("Lose weight");
        given()
            .contentType(ContentType.JSON)
            .header("X-Invite-Token", Jwt.claims().sign())
            .body(request)
            .when()
            .post("/auth/accept-invite")
            .then()
            .statusCode(anyOf(is(400), is(401)));
    }

    @Test
    void acceptInvite_shouldReturn400_whenMissingRequiredFields() {
        String token = Jwt.claims()
                .claim("sub", "user-id-123")
                .claim("email", "test@example.com")
                .issuer("https://mycard.io")
                .audience("client-invite")
                .sign();

        RegisterClientRequest request = new RegisterClientRequest();
        // missing fullName, age, heightCm, weightKg
        given()
            .contentType(ContentType.JSON)
            .header("X-Invite-Token", token)
            .body(request)
            .when()
            .post("/auth/accept-invite")
            .then()
            .statusCode(400);
    }

    @Test
    void acceptInvite_shouldReturn400_whenInvalidAge() {
        String token = Jwt.claims()
                .claim("sub", "user-id-123")
                .claim("email", "test@example.com")
                .issuer("https://mycard.io")
                .audience("client-invite")
                .sign();

        RegisterClientRequest request = new RegisterClientRequest();
        request.setFullName("Test User");
        request.setAge(10); // too young
        request.setHeightCm(180.0);
        request.setWeightKg(75.0);
        request.setGoalDescription("Lose weight");
        given()
            .contentType(ContentType.JSON)
            .header("X-Invite-Token", token)
            .body(request)
            .when()
            .post("/auth/accept-invite")
            .then()
            .statusCode(400);
    }

    @Test
    void acceptInvite_shouldReturn400_whenNegativeHeightOrWeight() {
        String token = Jwt.claims()
                .claim("sub", "user-id-123")
                .claim("email", "test@example.com")
                .issuer("https://mycard.io")
                .audience("client-invite")
                .sign();

        RegisterClientRequest request = new RegisterClientRequest();
        request.setFullName("Test User");
        request.setAge(25);
        request.setHeightCm(-180.0);
        request.setWeightKg(-75.0);
        request.setGoalDescription("Lose weight");
        given()
            .contentType(ContentType.JSON)
            .header("X-Invite-Token", token)
            .body(request)
            .when()
            .post("/auth/accept-invite")
            .then()
            .statusCode(400);
    }
} 