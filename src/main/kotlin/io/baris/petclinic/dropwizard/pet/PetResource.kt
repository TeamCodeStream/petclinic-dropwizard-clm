package io.baris.petclinic.dropwizard.pet

import com.newrelic.api.agent.Trace
import io.baris.petclinic.dropwizard.pet.model.CreatePetRequest
import io.baris.petclinic.dropwizard.pet.model.Pet
import io.baris.petclinic.dropwizard.pet.model.UpdatePetRequest
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
 * Pet resource to serve pet endpoints
 */
@Path("/pets")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class PetResource(private val petManager: PetManager) {

    @Operation(
        summary = "Get pet",
        tags = ["Pet"],
        responses = [
            ApiResponse(
                description = "The pet",
                content = [Content(schema = Schema(implementation = Pet::class))]
            ), ApiResponse(responseCode = "404", description = "Pet not found")
        ]
    )
    @GET
    @Path("/{id}")
    fun getPet(@PathParam("id") id: Int): Pet {
        return petManager.getPet(id).orElseThrow { NotFoundException("Pet not found") }
    }

    @get:Trace
    @get:GET
    @get:Operation(
        summary = "Get all pets",
        tags = ["Pet"],
        responses = [
            ApiResponse(
                description = "All pets",
                content = [Content(array = ArraySchema(schema = Schema(implementation = Pet::class)))]
            )
        ]
    )
    val allPets: List<Pet>
        get() = petManager.getAllPets()

    @Operation(
        summary = "Create pet",
        tags = ["Pet"],
        responses = [
            ApiResponse(
                description = "The pet",
                content = [Content(schema = Schema(implementation = Pet::class))]
            ), ApiResponse(responseCode = "422", description = "Invalid input"), ApiResponse(
                responseCode = "500",
                description = "Pet could not be created"
            )
        ]
    )
    @POST
    fun createPet(createPetRequest: @Valid CreatePetRequest): Pet {
        return petManager.createPet(PetMapper.mapToCreatePet(createPetRequest)).orElseThrow {
            InternalServerErrorException(
                "Pet could not be created"
            )
        }
    }

    @Operation(
        summary = "Update pet",
        tags = ["Pet"],
        responses = [
            ApiResponse(
                description = "The pet",
                content = [Content(schema = Schema(implementation = Pet::class))]
            ), ApiResponse(responseCode = "422", description = "Invalid input"), ApiResponse(
                responseCode = "404",
                description = "Pet not found"
            ), ApiResponse(responseCode = "500", description = "Pet could not be updated")
        ]
    )
    @Path("{id}")
    @PUT
    fun updatePet(@PathParam("id") id: Int, updatePetRequest: @Valid UpdatePetRequest): Pet {
        return petManager.updatePet(PetMapper.mapToUpdatePet(id, updatePetRequest)).orElseThrow {
            NotFoundException(
                "Pet not found"
            )
        }
    }
}
