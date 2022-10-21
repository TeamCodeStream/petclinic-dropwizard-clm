package io.baris.petclinic.dropwizard.vet

import io.baris.petclinic.dropwizard.vet.model.CreateVet
import io.baris.petclinic.dropwizard.vet.model.CreateVetRequest
import io.baris.petclinic.dropwizard.vet.model.UpdateVet
import io.baris.petclinic.dropwizard.vet.model.UpdateVetRequest

/**
 * Maps vet api classes
 */
object VetMapper {
    fun mapToUpdateVet(
        id: Int,
        updateVetRequest: UpdateVetRequest
    ): UpdateVet {
        return UpdateVet(
            id = id,
            name = updateVetRequest.name,
            specialties = updateVetRequest.specialties
        )
    }

    fun mapToCreateVet(
        createVetRequest: CreateVetRequest
    ): CreateVet {
        return CreateVet(
            name = createVetRequest.name,
            specialties = createVetRequest.specialties
        )
    }
}
