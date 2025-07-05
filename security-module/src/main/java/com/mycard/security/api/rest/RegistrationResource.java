package com.mycard.security.api.rest;

import com.mycard.security.api.dto.InviteClientRequest;
import com.mycard.security.api.dto.RegisterClientRequest;
import com.mycard.security.api.mapper.InviteResourceMapper;
import com.mycard.security.application.service.InvitationService;
import com.mycard.security.application.service.RegistrationService;
import io.smallrye.jwt.auth.principal.ParseException;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;
import java.util.UUID;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Authentication", description = "Security-related operations")
public class RegistrationResource {

    @Inject
    InvitationService invitationService;

    @Inject
    RegistrationService registrationService;

    @Inject
    InviteResourceMapper mapper;

    @POST
    @Path("/invite-client")
    @Operation(summary = "Invite a client to register")
    public Response inviteClient(@Valid InviteClientRequest request) {
        UUID inviteId = invitationService.inviteClient(mapper.toCommand(request));
        return Response.created(URI.create("/auth/invite-client/" + inviteId)).build();
    }

    @POST
    @Path("/accept-invite")
    @Operation(summary = "Accept client invitation and register")
    public Response acceptInvite(@QueryParam("token") String token, @Valid RegisterClientRequest request) throws ParseException {
        registrationService.registerClientFromToken(token, request);
        return Response.ok().build();
    }
}
