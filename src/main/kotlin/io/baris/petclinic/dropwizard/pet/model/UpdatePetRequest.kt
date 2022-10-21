package io.baris.petclinic.dropwizard.pet.model

import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

/**
 * Represents the request for updating the pet
 */
class UpdatePetRequest(
    var name: @NotEmpty String,
    @Min(value = 1, message = "From must be greater than zero")
    var age: Int,
    var species: @NotNull Species
)
