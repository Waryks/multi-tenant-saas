package com.mycard.nutrition.api.rest;

import com.mycard.nutrition.api.dto.AssignNutritionPlanRequest;
import com.mycard.nutrition.api.dto.NutritionPlanDTO;
import com.mycard.nutrition.api.mapper.NutritionResourceMapper;
import com.mycard.nutrition.application.service.NutritionService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;
import java.util.UUID;

@Path("/nutrition-plans")
@Tag(name = "Nutrition", description = "Nutrition plan management")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NutritionResource {

    @Inject
    NutritionService service;

    @Inject
    NutritionResourceMapper mapper;

    @POST
    public Response assignPlan(@Valid AssignNutritionPlanRequest request) {
        var command = mapper.toCommand(request);
        var id = service.assignNutritionPlan(command);
        var plan = service.findById(id).orElseThrow();
        NutritionPlanDTO dto = mapper.toDto(plan);

        return Response.created(URI.create("/nutrition-plans/" + id)).entity(dto).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") UUID id) {
        return service.findById(id)
                .map(mapper::toDto)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }
}
