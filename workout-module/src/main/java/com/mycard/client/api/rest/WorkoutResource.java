package com.mycard.client.api.rest;

import com.mycard.client.api.dto.AssignWorkoutRequest;
import com.mycard.client.api.mapper.WorkoutResourceMapper;
import com.mycard.client.application.service.WorkoutService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;
import java.util.UUID;

@Tag(name = "Workout", description = "Workout management")
@Path("/workouts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WorkoutResource {

    @Inject
    WorkoutService workoutService;

    @Inject
    WorkoutResourceMapper mapper;

    @POST
    @Operation(summary = "Assign a workout to a client")
    public Response assignWorkout(@Valid AssignWorkoutRequest request) {
        var command = mapper.toCommand(request);
        var id = workoutService.assignWorkout(command);
        var dto = mapper.toDto(workoutService.findById(id).orElseThrow());
        return Response.created(URI.create("/workouts/" + id)).entity(dto).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get workout by ID")
    public Response getWorkout(@PathParam("id") UUID id) {
        return workoutService.findById(id)
                .map(mapper::toDto)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @GET
    @Path("/clients/{clientId}")
    @Operation(summary = "Get all workouts assigned to a client")
    public Response getWorkoutsForClient(@PathParam("clientId") UUID clientId) {
        var workouts = workoutService.findAllByClientId(clientId);
        var dtoList = workouts.stream().map(mapper::toDto).toList();
        return Response.ok(dtoList).build();
    }
}
