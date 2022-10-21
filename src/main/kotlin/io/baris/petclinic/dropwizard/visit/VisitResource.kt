package io.baris.petclinic.dropwizard.visit

import io.baris.petclinic.dropwizard.pet.PetManager
import io.baris.petclinic.dropwizard.vet.VetManager
import io.baris.petclinic.dropwizard.visit.model.MakeVisitRequest
import io.baris.petclinic.dropwizard.visit.model.Visit
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import javax.validation.Valid
import javax.ws.rs.BadRequestException
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.InternalServerErrorException
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

/**
 * Visit resource to serve visit endpoints
 */
@Path("/visits")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class VisitResource(
    private val visitManager: VisitManager,
    private val petManager: PetManager,
    private val vetManager: VetManager
) {

    @Operation(
        summary = "Make visit",
        tags = ["Visit"],
        responses = [
            ApiResponse(
                description = "The visit",
                content = [Content(schema = Schema(implementation = Visit::class))]
            ), ApiResponse(responseCode = "400", description = "Invalid parameter"), ApiResponse(
                responseCode = "422",
                description = "Invalid input"
            ), ApiResponse(responseCode = "500", description = "Visit could not be created")
        ]
    )
    @POST
    @Path("/pets/{petId}/vets/{vetId}")
    fun makeVisit(
        @PathParam("petId") petId: Int,
        @PathParam("vetId") vetId: Int,
        createPetRequest: @Valid MakeVisitRequest
    ): Visit {
        // validation
        petManager.getPet(petId)
            .orElseThrow { BadRequestException("Pet does not exist") }
        vetManager.getVet(vetId)
            .orElseThrow { BadRequestException("Pet does not exist") }
        return visitManager
            .makeVisit(VisitMapper.mapToMakeVisit(petId, vetId, createPetRequest))
            .orElseThrow { InternalServerErrorException("Visit could not be created") }
    }

    @Operation(
        summary = "Get visits for a pet",
        tags = ["Visit"],
        responses = [
            ApiResponse(
                description = "Visits for a pet",
                content = [Content(array = ArraySchema(schema = Schema(implementation = Visit::class)))]
            )
        ]
    )
    @GET
    @Path("/pets/{petId}")
    fun getPetVisits(
        @PathParam("petId") petId: Int
    ): List<Visit> {
        // validation
        petManager.getPet(petId)
            .orElseThrow { BadRequestException("Pet does not exist") }
        return visitManager.getPetVisits(petId)
    }
}
