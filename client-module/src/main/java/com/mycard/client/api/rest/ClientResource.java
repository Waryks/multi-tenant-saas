package com.mycard.client.api.rest;


import com.mycard.client.api.dto.ClientDTO;
import com.mycard.client.api.dto.RegisterClientRequest;
import com.mycard.client.api.mapper.ClientResourceMapper;
import com.mycard.client.application.service.ClientService;
import com.mycard.client.application.command.RegisterClientCommand;
import com.mycard.client.domain.model.Client;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.List;
import java.util.UUID;

@Path("/clients")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClientResource {

    @Inject
    ClientService clientService;
    @Inject
    ClientResourceMapper mapper;
    @POST
    @Transactional
    public Response registerClient(@Valid RegisterClientRequest request) {
        RegisterClientCommand command = mapper.toCommand(request);
        UUID clientId = clientService.registerClient(command);

        return clientService.findById(clientId)
                .map(client -> Response.status(Response.Status.CREATED).entity(mapper.toDto(client)).build())
                .orElse(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    @GET
    @Path("/{id}")
    public Response getClientById(@PathParam("id") UUID id) {
        return clientService.findById(id)
                .map(client -> Response.ok(client).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateClient(@PathParam("id") UUID id, @Valid RegisterClientRequest request) {
        RegisterClientCommand command = mapper.toCommand(request);
        boolean updated = clientService.updateClient(id, command);

        if (updated) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteClient(@PathParam("id") UUID id) {
        boolean deleted = clientService.deleteClient(id);

        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/by-trainer/{trainerId}")
    @Operation(summary = "Get all clients by trainer ID")
    @APIResponse(responseCode = "200", description = "Clients found")
    @APIResponse(responseCode = "404", description = "No clients found")
    public Response getClientsByTrainer(@PathParam("trainerId") UUID trainerId) {
        List<Client> clients = clientService.findByTrainerId(trainerId);

        if (clients.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<ClientDTO> response = clients.stream().map(mapper::toDto).toList();
        return Response.ok(response).build();
    }
}