package io.baris.petclinic.dropwizard.vet.model

/**
 * Model for updating the vet
 */
data class UpdateVet(
    val id: Int,
    val name: String,
    val specialties: Set<String>
)
