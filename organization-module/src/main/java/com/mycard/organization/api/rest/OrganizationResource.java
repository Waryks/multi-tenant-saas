package com.mycard.organization.api.rest;

import com.mycard.organization.api.dto.CreateOrganizationRequest;
import com.mycard.organization.api.dto.OrganizationDTO;
import com.mycard.organization.api.mapper.OrganizationResourceMapper;
import com.mycard.organization.application.service.OrganizationService;

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

@Path("/organizations")
@Tag(name = "Organization", description = "Organization management operations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrganizationResource {

    @Inject
    OrganizationService service;

    @Inject
    OrganizationResourceMapper mapper;

    @POST
    @Operation(summary = "Create a new organization")
    @APIResponse(responseCode = "201", description = "Organization created successfully")
    @APIResponse(responseCode = "400", description = "Invalid input")
    public Response create(@Valid CreateOrganizationRequest request) {
        UUID id = service.createOrganization(mapper.toCommand(request));
        OrganizationDTO dto = mapper.toDto(service.findById(id).orElseThrow());

        return Response.created(URI.create("/organizations/" + id)).entity(dto).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get organization by ID")
    @APIResponse(responseCode = "200", description = "Organization found")
    @APIResponse(responseCode = "404", description = "Not found")
    public Response get(@PathParam("id") UUID id) {
        return service.findById(id)
                .map(mapper::toDto)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }
}
