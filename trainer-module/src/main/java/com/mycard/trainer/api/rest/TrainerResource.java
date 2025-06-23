package com.mycard.trainer.api.rest;

import com.mycard.trainer.api.dto.CreateTrainerRequest;
import com.mycard.trainer.api.dto.TrainerDTO;
import com.mycard.trainer.application.service.CreateTrainerCommand;
import com.mycard.trainer.application.service.TrainerService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;
import java.util.UUID;

@Tag(name = "Trainer", description = "Trainer management operations")
@Path("/trainers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TrainerResource {

    @Inject
    TrainerService trainerService;

    @POST
    @Operation(summary = "Create a new trainer")
    @APIResponse(responseCode = "201", description = "Trainer created successfully")
    @APIResponse(responseCode = "400", description = "Invalid input")
    public Response createTrainer(@Valid CreateTrainerRequest request) {
        CreateTrainerCommand command = new CreateTrainerCommand(
                request.getFullName(),
                request.getEmail(),
                request.getOrganizationId(),
                request.getBio(),
                request.getPhoneNumber(),
                request.getSpecialization(),
                request.getInstagramHandle(),
                request.getProfilePictureUrl()
        );

        UUID trainerId = trainerService.createTrainer(command);

        TrainerDTO response = new TrainerDTO(
                trainerId,
                request.getFullName(),
                request.getEmail(),
                request.getBio(),
                request.getSpecialization(),
                request.getInstagramHandle()
        );

        return Response.created(URI.create("/trainers/" + trainerId)).entity(response).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get trainer by ID", description = "Returns a trainer by its unique ID")
    @APIResponse(responseCode = "200", description = "Trainer found")
    @APIResponse(responseCode = "404", description = "Trainer not found")
    public Response getTrainerById(@PathParam("id") UUID id) {
        return trainerService.findById(id)
                .map(trainer -> {
                    TrainerDTO dto = new TrainerDTO(
                            trainer.getId(),
                            trainer.getFullName(),
                            trainer.getEmail(),
                            trainer.getProfile().getBio(),
                            trainer.getProfile().getSpecialization(),
                            trainer.getProfile().getInstagramHandle()
                    );
                    return Response.ok(dto).build();
                })
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
}

