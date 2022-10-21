package io.baris.petclinic.dropwizard.pet.model

import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty

/**
 * Represents the request for creating a new pet
 */
class CreatePetRequest(
    val name: @NotEmpty String,
    @Min(value = 1, message = "From must be greater than zero")
    val age: Int,
    val species: Species
)
