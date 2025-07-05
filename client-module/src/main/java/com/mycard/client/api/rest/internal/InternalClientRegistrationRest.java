package com.mycard.client.api.rest.internal;

import com.mycard.client.api.dto.ClientProfileDTO;
import com.mycard.client.api.dto.RegisterClientRequest;
import com.mycard.client.application.command.RegisterClientCommand;
import com.mycard.client.application.service.ClientService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.UUID;

@Path("/internal/clients")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Internal", description = "Internal ACL operations for client registration")
public class InternalClientRegistrationRest {

    @Inject
    ClientService clientService;

    @POST
    @Path("/from-invite")
    public Response createFromInvite(
            @QueryParam("trainerId") UUID trainerId,
            @QueryParam("email") String email,
            @Valid RegisterClientRequest request
    ) {
        ClientProfileDTO p = request.getProfile();

        RegisterClientCommand command = new RegisterClientCommand(
                trainerId,
                request.getFullName(),
                email,
                p.getAge(),
                p.getHeightCm(),
                p.getWeightKg(),
                p.getGoalDescription()
        );

        UUID clientId = clientService.registerClient(command);

        return Response.status(Response.Status.CREATED).entity(clientId).build();
    }
}
