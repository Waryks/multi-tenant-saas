package com.mycard.workout.api.rest;

import com.mycard.workout.api.dto.AssignWorkoutRequest;
import com.mycard.workout.api.dto.ExerciseDTO;
import com.mycard.workout.application.event.IWorkoutEventPublisher;
import com.mycard.workout.domain.event.WorkoutAssignedEvent;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class WorkoutResourceTest {

    @InjectMock
    IWorkoutEventPublisher eventPublisher;

    @BeforeEach
    void setupMocks() {
        Mockito.reset(eventPublisher);
    }

    @AfterEach
    void tearDownMocks() {
        Mockito.reset(eventPublisher);
    }

    @Test
    void assignWorkout_shouldReturn201_whenValidRequest() {
        AssignWorkoutRequest request = new AssignWorkoutRequest();
        request.setTrainerId(UUID.randomUUID());
        request.setClientId(UUID.randomUUID());
        request.setTitle("Leg Day");
        request.setDescription("Leg workout");
        request.setScheduledDate(LocalDate.now().plusDays(1));
        ExerciseDTO exercise = new ExerciseDTO();
        exercise.setName("Squat");
        exercise.setSets(4);
        exercise.setReps(10);
        exercise.setRestSeconds(60);
        request.setExercises(List.of(exercise));

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .post("/workouts")
            .then()
            .statusCode(201)
            .body("title", equalTo("Leg Day"))
            .body("exercises[0].name", equalTo("Squat"));
    }

    @Test
    void assignWorkout_shouldReturn400_whenMissingOrInvalidFields() {
        AssignWorkoutRequest request = new AssignWorkoutRequest();

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .post("/workouts")
            .then()
            .statusCode(400);
    }

    @Test
    void getWorkoutById_shouldReturn404_whenNotFound() {
        UUID nonExistentId = UUID.randomUUID();

        given()
            .when()
            .get("/workouts/" + nonExistentId)
            .then()
            .statusCode(404);
    }

    @Test
    void getWorkoutById_shouldReturn200_whenFound() {
        AssignWorkoutRequest request = new AssignWorkoutRequest();
        request.setTrainerId(UUID.randomUUID());
        request.setClientId(UUID.randomUUID());
        request.setTitle("Push Day");
        request.setDescription("Push workout");
        request.setScheduledDate(LocalDate.now().plusDays(2));
        ExerciseDTO exercise = new ExerciseDTO();
        exercise.setName("Bench Press");
        exercise.setSets(3);
        exercise.setReps(8);
        exercise.setRestSeconds(90);
        request.setExercises(List.of(exercise));

        String location = given()
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .post("/workouts")
            .then()
            .statusCode(201)
            .extract().header("Location");

        given()
            .when()
            .get(location)
            .then()
            .statusCode(200)
            .body("title", equalTo("Push Day"))
            .body("exercises[0].name", equalTo("Bench Press"));
    }

    @Test
    void getWorkoutsForClient_shouldReturn200_whenEmptyList() {
        UUID clientId = UUID.randomUUID();
        given()
            .when()
            .get("/workouts/clients/" + clientId)
            .then()
            .statusCode(200)
            .body("size()", is(0));
    }

    @Test
    void getWorkoutsForClient_shouldReturn200_whenMultipleWorkouts() {
        UUID clientId = UUID.randomUUID();
        AssignWorkoutRequest request1 = new AssignWorkoutRequest();
        request1.setTrainerId(UUID.randomUUID());
        request1.setClientId(clientId);
        request1.setTitle("Day 1");
        request1.setDescription("Workout 1");
        request1.setScheduledDate(LocalDate.now().plusDays(1));
        ExerciseDTO ex1 = new ExerciseDTO();
        ex1.setName("Deadlift");
        ex1.setSets(5);
        ex1.setReps(5);
        ex1.setRestSeconds(120);
        request1.setExercises(List.of(ex1));

        AssignWorkoutRequest request2 = new AssignWorkoutRequest();
        request2.setTrainerId(UUID.randomUUID());
        request2.setClientId(clientId);
        request2.setTitle("Day 2");
        request2.setDescription("Workout 2");
        request2.setScheduledDate(LocalDate.now().plusDays(2));
        ExerciseDTO ex2 = new ExerciseDTO();
        ex2.setName("Pull Up");
        ex2.setSets(4);
        ex2.setReps(8);
        ex2.setRestSeconds(90);
        request2.setExercises(List.of(ex2));

        given().contentType(ContentType.JSON).body(request1).when().post("/workouts").then().statusCode(201);
        given().contentType(ContentType.JSON).body(request2).when().post("/workouts").then().statusCode(201);

        given()
            .when()
            .get("/workouts/clients/" + clientId)
            .then()
            .statusCode(200)
            .body("size()", is(2))
            .body("title", hasItems("Day 1", "Day 2"));
    }

    @Test
    void assignWorkout_shouldPersistAndPublishEvent_integration() {
        AssignWorkoutRequest request = new AssignWorkoutRequest();
        request.setTrainerId(UUID.randomUUID());
        request.setClientId(UUID.randomUUID());
        request.setTitle("Integration Test");
        request.setDescription("Integration");
        request.setScheduledDate(LocalDate.now().plusDays(1));
        ExerciseDTO exercise = new ExerciseDTO();
        exercise.setName("Burpee");
        exercise.setSets(3);
        exercise.setReps(15);
        exercise.setRestSeconds(30);
        request.setExercises(List.of(exercise));

        String location = given()
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .post("/workouts")
            .then()
            .statusCode(201)
            .extract().header("Location");

        given()
            .when()
            .get(location)
            .then()
            .statusCode(200)
            .body("title", equalTo("Integration Test"));

        Mockito.verify(eventPublisher, Mockito.atLeastOnce()).publish(Mockito.any(WorkoutAssignedEvent.class));
    }
} 