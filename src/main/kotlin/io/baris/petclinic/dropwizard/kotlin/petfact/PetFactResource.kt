package io.baris.petclinic.dropwizard.kotlin.petfact

import io.baris.petclinic.dropwizard.kotlin.petfact.model.PetFactResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import lombok.extern.slf4j.Slf4j
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

/**
 * Pet Fact resource to serve visit endpoints
 */
@Path("/clm/kotlin/facts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
class PetFactResource(
        val petFactService: PetFactManager
) {

    @GET
    @Operation(
            summary = "Get pet facts",
            tags = ["Pet Fact"],
            responses = [ApiResponse(description = "Facts for pets",
                    content = [Content(schema = Schema(implementation = PetFactResponse::class))])])
    fun petVisits(): PetFactResponse {
        return petFactService.petFacts()
    }
}