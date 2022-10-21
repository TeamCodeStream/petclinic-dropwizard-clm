package io.baris.petclinic.dropwizard.vet.model

import javax.validation.constraints.NotEmpty

/**
 * Represents the request for creating a new vet
 */
data class CreateVetRequest(
    val name: String,
    val specialties: @NotEmpty MutableSet<String>
)
