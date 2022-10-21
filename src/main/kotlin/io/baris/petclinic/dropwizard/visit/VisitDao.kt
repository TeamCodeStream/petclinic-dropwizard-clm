package io.baris.petclinic.dropwizard.visit

import io.baris.petclinic.dropwizard.visit.model.MakeVisit
import io.baris.petclinic.dropwizard.visit.model.Visit
import org.jdbi.v3.sqlobject.kotlin.RegisterKotlinMapper
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.jdbi.v3.sqlobject.transaction.Transaction
import java.time.Instant
import java.util.*

/**
 * Manages visits in the database
 */
interface VisitDao {
    @SqlQuery("SELECT * FROM visits WHERE id = ?")
    @RegisterKotlinMapper(Visit::class)
    fun getVisit(id: Int): Visit

    @SqlQuery("SELECT * FROM visits WHERE pet_id = ?")
    @RegisterKotlinMapper(Visit::class)
    fun getPetVisits(petId: Int): List<Visit>

    @SqlUpdate("INSERT INTO visits (pet_id, vet_id, date, treatment) VALUES (?, ?, ?, ?) returning *")
    @GetGeneratedKeys
    fun createVisit(petId: Int, vetId: Int, date: Instant, treatment: String): Int

    @Transaction
    fun createVisit(makeVisit: MakeVisit): Optional<Visit> {
        val visitId = createVisit(
            makeVisit.petId,
            makeVisit.vetId,
            makeVisit.date,
            makeVisit.treatment
        )
        return Optional.of(getVisit(visitId))
    }
}
