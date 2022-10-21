package io.baris.petclinic.dropwizard.visit

import io.baris.petclinic.dropwizard.visit.model.MakeVisit
import io.baris.petclinic.dropwizard.visit.model.MakeVisitRequest

/**
 * Maps visit api classes
 */
object VisitMapper {
    fun mapToMakeVisit(
        petId: Int,
        vetId: Int,
        makeVisitRequest: MakeVisitRequest
    ): MakeVisit {
        return MakeVisit(
            petId = petId,
            vetId = vetId,
            date = makeVisitRequest.date,
            treatment = makeVisitRequest.treatment
        )
    }
}
