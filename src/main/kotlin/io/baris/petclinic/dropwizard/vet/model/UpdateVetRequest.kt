package io.baris.petclinic.dropwizard.vet.model

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

/**
 * Represents the request for updating the vet
 */
data class UpdateVetRequest(
    val name: @NotNull String,
    val specialties: @NotEmpty MutableSet<String>
)
