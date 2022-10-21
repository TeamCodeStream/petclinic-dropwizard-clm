package io.baris.petclinic.dropwizard.visit

import io.baris.petclinic.dropwizard.visit.model.MakeVisit
import io.baris.petclinic.dropwizard.visit.model.Visit
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.withExtensionUnchecked
import java.util.*

/**
 * Manages the visits
 */
class VisitManager(private val jdbi: Jdbi) {

    fun makeVisit(makeVisit: MakeVisit): Optional<Visit> {
        return jdbi.withExtensionUnchecked(VisitDao::class.java) { dao: VisitDao -> dao.createVisit(makeVisit) }
    }

    fun getPetVisits(petId: Int): List<Visit> {
        return jdbi.withExtensionUnchecked(VisitDao::class.java) { dao: VisitDao -> dao.getPetVisits(petId) }
    }
}
