package io.baris.petclinic.dropwizard.pet.model

/**
 * Represents a pet
 */
data class Pet(
    val id: Int,
    val name: String,
    val age: Int,
    val species: Species
)
