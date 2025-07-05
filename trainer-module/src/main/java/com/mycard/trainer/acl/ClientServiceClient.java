package com.mycard.trainer.acl;

import com.mycard.trainer.acl.dto.ClientDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
@RegisterRestClient(configKey = "client-service")
@Produces(MediaType.APPLICATION_JSON)
public interface ClientServiceClient {

    @GET
    @Path("/clients/by-trainer/{trainerId}")
    List<ClientDTO> getClientsByTrainer(@PathParam("trainerId") UUID trainerId);
}
