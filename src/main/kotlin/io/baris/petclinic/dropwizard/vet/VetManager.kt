package io.baris.petclinic.dropwizard.vet

import io.baris.petclinic.dropwizard.vet.model.CreateVet
import io.baris.petclinic.dropwizard.vet.model.UpdateVet
import io.baris.petclinic.dropwizard.vet.model.Vet
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.withExtensionUnchecked
import java.util.*

/**
 * Manages the vet
 */
class VetManager(private val jdbi: Jdbi) {

    fun getVet(vetId: Int): Optional<Vet> {
        return jdbi.withExtensionUnchecked(VetDao::class.java) { dao: VetDao -> dao.getVet(vetId) }
    }

    fun getVet(name: String): Optional<Vet> {
        return jdbi.withExtensionUnchecked(VetDao::class.java) { dao: VetDao -> dao.getVet(name) }
    }

    fun getAllVets(): List<Vet> {
        return jdbi.withExtensionUnchecked(VetDao::class.java) { obj: VetDao -> obj.getAllVets() }
    }

    fun createVet(createVet: CreateVet): Optional<Vet> {
        return jdbi.withExtensionUnchecked(VetDao::class.java) { dao: VetDao -> dao.createVet(createVet) }
    }

    fun updateVet(updateVet: UpdateVet): Optional<Vet> {
        return jdbi.withExtensionUnchecked(VetDao::class.java) { dao: VetDao -> dao.updateVet(updateVet) }
    }
}
