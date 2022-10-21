package io.baris.petclinic.dropwizard.homepage

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

/**
 * Home resource to serve homepage
 */
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.TEXT_HTML)
class HomepageResource {
    @Operation(
        summary = "Homepage",
        tags = ["Homepage"],
        responses = [
            ApiResponse(
                description = "Homepage",
                content = [Content(schema = Schema(implementation = String::class))]
            )
        ]
    )
    @GET
    fun homepage(): String {
        return "Welcome to Pet Clinic"
    }
}
