package io.baris.petclinic.dropwizard.pet

import io.baris.petclinic.dropwizard.pet.model.CreatePet
import io.baris.petclinic.dropwizard.pet.model.CreatePetRequest
import io.baris.petclinic.dropwizard.pet.model.UpdatePet
import io.baris.petclinic.dropwizard.pet.model.UpdatePetRequest

/**
 * Maps pet api classes
 */
object PetMapper {
    fun mapToUpdatePet(
        id: Int,
        updatePetRequest: UpdatePetRequest
    ): UpdatePet {
        return UpdatePet(
            id = id,
            age = updatePetRequest.age,
            species = updatePetRequest.species,
            name = updatePetRequest.name
        )
    }

    fun mapToCreatePet(
        createPetRequest: CreatePetRequest
    ): CreatePet {
        return CreatePet(
            name = createPetRequest.name,
            age = createPetRequest.age,
            species = createPetRequest.species
        )
    }
}
