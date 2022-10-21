package io.baris.petclinic.dropwizard.vet

import io.baris.petclinic.dropwizard.vet.model.CreateVetRequest
import io.baris.petclinic.dropwizard.vet.model.UpdateVetRequest
import io.baris.petclinic.dropwizard.vet.model.Vet
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.InternalServerErrorException
import javax.ws.rs.NotFoundException
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

/**
 * Vet resource to serve vet endpoints
 */
@Path("/vets")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class VetResource(private val vetManager: VetManager) {
    @Operation(
        summary = "Get vet",
        tags = ["Vet"],
        responses = [
            ApiResponse(
                description = "The vet",
                content = [Content(schema = Schema(implementation = Vet::class))]
            ), ApiResponse(responseCode = "404", description = "Vet not found")
        ]
    )
    @GET
    @Path("/{id}")
    fun getVet(@PathParam("id") id: Int): Vet {
        return vetManager.getVet(id).orElseThrow { NotFoundException("Vet not found") }
    }

    @GET
    @Operation(
        summary = "Get all vets",
        tags = ["Vet"],
        responses = [
            ApiResponse(
                description = "All vets",
                content = [Content(array = ArraySchema(schema = Schema(implementation = Vet::class)))]
            )
        ]
    )
    fun getAllVets(): List<Vet> {
        return vetManager.getAllVets()
    }

    @Operation(
        summary = "Create vet",
        tags = ["Vet"],
        responses = [
            ApiResponse(
                description = "The vet",
                content = [Content(schema = Schema(implementation = Vet::class))]
            ), ApiResponse(responseCode = "422", description = "Invalid input"), ApiResponse(
                responseCode = "500",
                description = "Vet could not be created"
            )
        ]
    )
    @POST
    fun createVet(createVetRequest: @Valid CreateVetRequest): Vet? {
        return vetManager.createVet(VetMapper.mapToCreateVet(createVetRequest)).orElseThrow {
            InternalServerErrorException(
                "Vet could not be created"
            )
        }
    }

    @Operation(
        summary = "Update vet",
        tags = ["Vet"],
        responses = [
            ApiResponse(
                description = "The vet",
                content = [Content(schema = Schema(implementation = Vet::class))]
            ), ApiResponse(responseCode = "422", description = "Invalid input"), ApiResponse(
                responseCode = "404",
                description = "Vet not found"
            ), ApiResponse(responseCode = "500", description = "Vet could not be updated")
        ]
    )
    @Path("{id}")
    @PUT
    fun updateVet(@PathParam("id") id: Int, updateVetRequest: @Valid UpdateVetRequest): Vet? {
        return vetManager.updateVet(VetMapper.mapToUpdateVet(id, updateVetRequest)).orElseThrow {
            NotFoundException(
                "Vet not found"
            )
        }
    }
}
