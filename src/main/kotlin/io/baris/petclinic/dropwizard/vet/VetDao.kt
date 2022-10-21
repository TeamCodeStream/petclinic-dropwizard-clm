package io.baris.petclinic.dropwizard.vet

import io.baris.petclinic.dropwizard.vet.model.CreateVet
import io.baris.petclinic.dropwizard.vet.model.UpdateVet
import io.baris.petclinic.dropwizard.vet.model.Vet
import org.jdbi.v3.sqlobject.kotlin.RegisterKotlinMapper
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.jdbi.v3.sqlobject.transaction.Transaction
import java.util.*
import java.util.function.Consumer
import java.util.function.Function

/**
 * Manages vet in the database
 */
interface VetDao {
    @SqlQuery("SELECT * FROM vets WHERE id = ?")
    @RegisterKotlinMapper(Vet::class)
    fun getVetBasic(vetId: Int): Vet?

    @SqlQuery("SELECT * FROM vets WHERE name = ?")
    @RegisterKotlinMapper(Vet::class)
    fun getVetBasic(name: String?): Vet?

    @RegisterKotlinMapper(Vet::class)
    @SqlQuery("SELECT * FROM vets ORDER BY name")
    fun getAllVetsBasic(): List<Vet>

    @SqlUpdate("INSERT INTO vets (name) VALUES (?) returning *")
    @GetGeneratedKeys
    fun createVetBasic(name: String): Int

    @SqlUpdate("UPDATE vets SET name = ? WHERE id = ?")
    fun updateVetBasic(name: String, id: Int)

    @SqlUpdate("INSERT INTO vet_specialties (vet_id, specialty) VALUES (?, ?)")
    fun createVetSpecialty(vetId: Int, specialty: String)

    @SqlUpdate("DELETE FROM vet_specialties WHERE vet_id = ?")
    fun deleteVetSpecialties(vetId: Int)

    @SqlQuery("SELECT specialty FROM vet_specialties WHERE vet_id = ?")
    fun getVetSpecialties(vetId: Int): Set<String>

    @RegisterKotlinMapper(Vet::class)
    @Transaction
    fun getVet(vetId: Int): Optional<Vet> {
        val vet = getVetBasic(vetId)
        if (vet != null) {
            vet.specialties = getVetSpecialties(vetId)
        }
        return Optional.ofNullable(vet)
    }

    @Transaction
    fun getVet(name: String): Optional<Vet> {
        val vet = getVetBasic(name)
        return if (vet != null) getVet(vet.id) else Optional.empty()
    }

    @Transaction
    fun getAllVets(): List<Vet> {
        return getAllVetsBasic()
            .stream()
            .map(Function<Vet, Optional<Vet>> { vet: Vet -> getVet(vet.id) })
            .filter { obj: Optional<Vet> -> obj.isPresent }
            .map { obj: Optional<Vet> -> obj.get() }
            .toList()
    }

    @Transaction
    fun createVet(createVet: CreateVet): Optional<Vet> {
        val vetId = createVetBasic(createVet.name)
        val specialties = createVet.specialties
        createVetSpecialties(vetId, specialties)
        return getVet(vetId)
    }

    @Transaction
    fun updateVet(updateVet: UpdateVet): Optional<Vet> {
        val vetId = updateVet.id
        updateVetBasic(updateVet.name, vetId)
        deleteVetSpecialties(vetId)
        createVetSpecialties(vetId, updateVet.specialties)
        return getVet(vetId)
    }

    @Transaction
    fun createVetSpecialties(vetId: Int, specialties: Set<String>) {
        specialties.forEach(Consumer { specialty: String -> createVetSpecialty(vetId, specialty) })
    }
}
