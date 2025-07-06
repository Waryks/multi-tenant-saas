package com.mycard.trainer.integration;

import com.mycard.trainer.api.dto.CreateTrainerRequest;
import com.mycard.trainer.api.dto.TrainerProfileDTO;
import com.mycard.trainer.api.rest.TrainerResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
@TestHTTPEndpoint(TrainerResource.class)
class TrainerIntegrationTest {

    @Test
    void shouldCreateTrainerAndFetchById() {
        CreateTrainerRequest request = new CreateTrainerRequest();
        request.setFullName("John Trainer");
        request.setEmail("john@trainer.com");
        request.setOrganizationId(UUID.randomUUID());
        TrainerProfileDTO profile = new TrainerProfileDTO();
        profile.setBio("Fit and ready");
        request.setProfile(profile);

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post()
                .then()
                .statusCode(201)
                .header("Location", notNullValue());
    }
}
