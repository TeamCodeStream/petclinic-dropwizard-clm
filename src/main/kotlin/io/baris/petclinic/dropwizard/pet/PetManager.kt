package io.baris.petclinic.dropwizard.pet

import io.baris.petclinic.dropwizard.pet.model.CreatePet
import io.baris.petclinic.dropwizard.pet.model.Pet
import io.baris.petclinic.dropwizard.pet.model.UpdatePet
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.withExtensionUnchecked
import java.util.*

/**
 * Manages the pet
 */
class PetManager(private val jdbi: Jdbi) {

    fun getPet(id: Int): Optional<Pet> {
        return jdbi.withExtensionUnchecked(PetDao::class.java) { dao: PetDao -> dao.getPet(id) }
    }

    fun getPet(name: String): Optional<Pet> {
        return jdbi.withExtensionUnchecked(PetDao::class.java) { dao: PetDao -> dao.getPet(name) }
    }

    fun getAllPets(): List<Pet> {
        return jdbi.withExtensionUnchecked(PetDao::class.java) { obj: PetDao -> obj.getAllPets() }
    }

    fun createPet(createPet: CreatePet): Optional<Pet> {
        return jdbi.withExtensionUnchecked(PetDao::class.java) { dao: PetDao -> dao.createPet(createPet) }
    }

    fun updatePet(updatePet: UpdatePet): Optional<Pet> {
        return jdbi.withExtensionUnchecked(PetDao::class.java) { dao: PetDao -> dao.updatePet(updatePet) }
    }
}
