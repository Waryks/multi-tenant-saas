package com.mycard.client.api.rest;


import com.mycard.client.api.dto.RegisterClientRequest;
import com.mycard.client.api.mapper.ClientResourceMapper;
import com.mycard.client.application.service.ClientService;
import com.mycard.client.application.service.RegisterClientCommand;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
}