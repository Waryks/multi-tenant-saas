package com.mycard.trainer.api.rest;

import com.mycard.trainer.acl.ClientServiceClient;
import com.mycard.trainer.acl.dto.ClientDTO;
import com.mycard.trainer.api.dto.CreateTrainerRequest;
import com.mycard.trainer.api.dto.TrainerDTO;
import com.mycard.trainer.api.mapper.TrainerResourceMapper;
import com.mycard.trainer.application.command.CreateTrainerCommand;
import com.mycard.trainer.application.service.TrainerService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Tag(name = "Trainer", description = "Trainer management operations")
@Path("/trainers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TrainerResource {

    @Inject
    TrainerService trainerService;

    @Inject
    TrainerResourceMapper mapper;

    @Inject
    @RestClient
    ClientServiceClient clientServiceClient;

    @POST
    @Operation(summary = "Create a new trainer")
    @APIResponse(responseCode = "201", description = "Trainer created successfully")
    @APIResponse(responseCode = "400", description = "Invalid input")
    public Response createTrainer(@Valid CreateTrainerRequest request) {
        CreateTrainerCommand command = mapper.toCommand(request);
        var trainer = trainerService.createTrainer(command);

        TrainerDTO dto = mapper.toDto(trainer);
        return Response.created(URI.create("/trainers/" + trainer.getId())).entity(dto).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get trainer by ID", description = "Returns a trainer by its unique ID")
    @APIResponse(responseCode = "200", description = "Trainer found")
    @APIResponse(responseCode = "404", description = "Trainer not found")
    public Response getTrainerById(@PathParam("id") UUID id) {
        return trainerService.findById(id)
                .map(trainer -> Response.ok(mapper.toDto(trainer)).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/{trainerId}/clients")
    @Operation(summary = "Get list of clients for a trainer")
    @APIResponse(responseCode = "200", description = "List of clients")
    @APIResponse(responseCode = "404", description = "No clients found")
    public Response getClients(@PathParam("trainerId") UUID trainerId) {
        List<ClientDTO> clients = clientServiceClient.getClientsByTrainer(trainerId);

        if (clients.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(clients).build();
    }
}

