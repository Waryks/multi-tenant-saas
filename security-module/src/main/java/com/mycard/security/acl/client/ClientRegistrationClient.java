package com.mycard.security.acl.client;

import com.mycard.security.api.dto.RegisterClientRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.UUID;

@ApplicationScoped
@RegisterRestClient(configKey = "client-registration")
@Path("/internal/clients")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ClientRegistrationClient {

    @POST
    @Path("/from-invite")
    void createClientFromInvite(
            @QueryParam("trainerId") UUID trainerId,
            @QueryParam("email") String email,
            RegisterClientRequest request
    );
}
