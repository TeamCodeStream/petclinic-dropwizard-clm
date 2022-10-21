package io.baris.petclinic.dropwizard.pet.model

/**
 * Model for updating the pet
 */
class UpdatePet(
    val id: Int,
    val name: String,
    val age: Int,
    val species: Species
)
