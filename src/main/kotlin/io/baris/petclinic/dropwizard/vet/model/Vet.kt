package io.baris.petclinic.dropwizard.vet.model

/**
 * Represents a vet
 */
data class Vet(
    val id: Int,
    val name: String,
    var specialties: Set<String>?
)
