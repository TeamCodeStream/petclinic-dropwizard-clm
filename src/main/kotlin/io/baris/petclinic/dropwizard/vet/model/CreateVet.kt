package io.baris.petclinic.dropwizard.vet.model

/**
 * Model for creating a vet
 */
data class CreateVet(
    val name: String,
    val specialties: Set<String>
)
