package io.baris.petclinic.dropwizard.petfact

import com.newrelic.api.agent.Trace
import io.baris.petclinic.dropwizard.Util.doWait
import io.baris.petclinic.dropwizard.petfact.model.PetFactResponse
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
 * Pet Fact resource to serve visit endpoints
 */
@Path("/clm/facts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class PetFactResource(
    private val petFactService: PetFactManager
) {

    @GET
    @Operation(
        summary = "Get pet facts",
        tags = ["Pet Fact"],
        responses = [
            ApiResponse(
                description = "Facts for pets",
                content = [Content(schema = Schema(implementation = PetFactResponse::class))]
            )
        ]
    )
    fun facts(): PetFactResponse {
        return petFactService.petFacts()
    }
}

@Path("/clm/dolphin/facts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class DolphinFactResource {

    @GET
    @Operation(
        summary = "Get dolphin facts",
        tags = ["Dolphin Fact"],
        responses = [
            ApiResponse(
                description = "Facts for dolphins",
                content = [Content(schema = Schema(implementation = DolphinResponse::class))]
            )
        ]
    )
    fun dolphinFact(): DolphinResponse {
        return getDolphinFact()
    }
}

private val DOLPHIN_FACTS = arrayOf(
    "The Amazon river is home to four species of river dolphin that are found nowhere else on Earth. ",
    "Marine traffic around the British Isles is amongst the most intense of anywhere in the world. " +
        "Noise pollution from naval activity, the oil and gas industry, seismic surveys and underwater " +
        "construction can stress and injure cetaceans. It also severely interferes with their ability to " +
        "communicate, reproduce, navigate and find prey - sometimes proving fatal. ",
    "Bottlenose dolphins are usually fairly slow swimmers, travelling at about 2 mph. However they can " +
        "reach speeds of over 30 mph for brief periods!",
    "When hunting dolphins produce bubbles to herd their prey to the surface. They sometimes also use a " +
        "hunting technique called 'fish-whacking', where they use their tail to hit fish and so stun " +
        "them - making them easier to catch."
)

@Trace
fun getDolphinFact(): DolphinResponse {
    doWait(300)
    return DolphinResponse(DOLPHIN_FACTS.random())
}

data class DolphinResponse(
    val fact: String
)
