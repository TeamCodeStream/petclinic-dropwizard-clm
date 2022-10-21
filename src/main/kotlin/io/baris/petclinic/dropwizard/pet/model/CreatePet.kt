package io.baris.petclinic.dropwizard.pet.model

/**
 * Model for creating a pet
 */
class CreatePet(
    val name: String,
    val age: Int,
    val species: Species
)
