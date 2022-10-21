package io.baris.petclinic.dropwizard.pet

import io.baris.petclinic.dropwizard.pet.model.CreatePet
import io.baris.petclinic.dropwizard.pet.model.Pet
import io.baris.petclinic.dropwizard.pet.model.Species
import io.baris.petclinic.dropwizard.pet.model.UpdatePet
import org.jdbi.v3.sqlobject.kotlin.RegisterKotlinMapper
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.jdbi.v3.sqlobject.transaction.Transaction
import java.util.*

/**
 * Manages pets in the database
 */
interface PetDao {
    @SqlQuery("SELECT * FROM pets WHERE id = ?")
    @RegisterKotlinMapper(Pet::class)
    fun getPetBasic(id: Int): Pet?

    @SqlQuery("SELECT * FROM pets WHERE name = ?")
    @RegisterKotlinMapper(Pet::class)
    fun getPetBasic(name: String): Pet?

    @RegisterKotlinMapper(Pet::class)
    @SqlQuery("SELECT * FROM pets ORDER BY name")
    fun getAllPets(): List<Pet>

    @SqlUpdate("INSERT INTO pets (name, age, species) VALUES (?, ?, ?) returning *")
    @GetGeneratedKeys
    fun createPet(name: String, age: Int, species: Species): Int

    @SqlUpdate("UPDATE pets SET name = ?,  age = ?,  species = ? WHERE id = ?")
    fun updatePet(name: String, age: Int, species: Species, id: Int)

    @Transaction
    fun getPet(id: Int): Optional<Pet> {
        return Optional.ofNullable(getPetBasic(id))
    }

    @Transaction
    fun getPet(name: String): Optional<Pet> {
        return Optional.ofNullable(getPetBasic(name))
    }

    @Transaction
    fun createPet(createPet: CreatePet): Optional<Pet> {
        val petId = createPet(
            createPet.name,
            createPet.age,
            createPet.species
        )
        return getPet(petId)
    }

    @Transaction
    fun updatePet(updatePet: UpdatePet): Optional<Pet> {
        updatePet(
            updatePet.name,
            updatePet.age,
            updatePet.species,
            updatePet.id
        )
        return getPet(updatePet.id)
    }
}
