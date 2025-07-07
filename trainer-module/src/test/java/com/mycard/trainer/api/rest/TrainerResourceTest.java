package com.mycard.trainer.api.rest;

import com.mycard.trainer.acl.dto.ClientDTO;
import com.mycard.trainer.acl.ClientServiceClient;
import com.mycard.trainer.api.dto.CreateTrainerRequest;
import com.mycard.trainer.api.dto.TrainerProfileDTO;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;

import io.restassured.http.ContentType;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class TrainerResourceTest {

    @InjectMock
    @RestClient
    ClientServiceClient clientServiceClient;

    @Test
    void testCreateTrainer() {
        CreateTrainerRequest request = new CreateTrainerRequest();
        request.setFullName("John Trainer");
        request.setEmail("john.trainer@mycard.io");
        request.setOrganizationId(UUID.randomUUID());

        TrainerProfileDTO profile = new TrainerProfileDTO();
        profile.setBio("Fitness enthusiast");
        profile.setPhoneNumber("123456789");
        profile.setSpecialization("Weightlifting");
        profile.setInstagramHandle("@trainerjohn");
        profile.setProfilePictureUrl("http://example.com/pic.jpg");

        request.setProfile(profile);

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/trainers")
                .then()
                .statusCode(201)
                .body("fullName", equalTo("John Trainer"))
                .body("email", equalTo("john.trainer@mycard.io"));
    }

    @Test
    void testGetClientsByTrainer() {
        UUID trainerId = UUID.randomUUID();

        ClientDTO mockClient = new ClientDTO();
        mockClient.setId(UUID.randomUUID());
        mockClient.setFullName("Jane Client");
        mockClient.setEmail("jane@client.com");
        mockClient.setTrainerId(trainerId);

        // Mock ACL call
        org.mockito.Mockito.when(clientServiceClient.getClientsByTrainer(trainerId))
                .thenReturn(List.of(mockClient));

        given()
                .when()
                .get("/trainers/" + trainerId + "/clients")
                .then()
                .statusCode(200)
                .body("[0].fullName", equalTo("Jane Client"))
                .body("[0].email", equalTo("jane@client.com"));
    }

    @Test
    void testCreateTrainer_MissingFields_ShouldReturn400() {
        CreateTrainerRequest request = new CreateTrainerRequest();
        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/trainers")
                .then()
                .statusCode(400);
    }

    @Test
    void testCreateTrainer_InvalidEmail_ShouldReturn400() {
        CreateTrainerRequest request = new CreateTrainerRequest();
        request.setFullName("Invalid Email");
        request.setEmail("not-an-email");
        request.setOrganizationId(UUID.randomUUID());
        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/trainers")
                .then()
                .statusCode(400);
    }

    @Test
    void testGetTrainerById_NotFound_ShouldReturn404() {
        UUID nonExistentId = UUID.randomUUID();
        given()
                .when()
                .get("/trainers/" + nonExistentId)
                .then()
                .statusCode(404);
    }

    @Test
    void testGetClientsByTrainer_EmptyList_ShouldReturn404() {
        UUID trainerId = UUID.randomUUID();
        org.mockito.Mockito.when(clientServiceClient.getClientsByTrainer(trainerId))
                .thenReturn(List.of());
        given()
                .when()
                .get("/trainers/" + trainerId + "/clients")
                .then()
                .statusCode(404);
    }

    @Test
    void testGetClientsByTrainer_AclClientThrows_ShouldReturn500() {
        UUID trainerId = UUID.randomUUID();
        org.mockito.Mockito.when(clientServiceClient.getClientsByTrainer(trainerId))
                .thenThrow(new RuntimeException("ACL error"));
        given()
                .when()
                .get("/trainers/" + trainerId + "/clients")
                .then()
                .statusCode(500);
    }
}
