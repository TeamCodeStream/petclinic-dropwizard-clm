package io.baris.petclinic.dropwizard.visit.model

import java.time.Instant

/**
 * Model for making a visit
 */
data class MakeVisit(
    val petId: Int,
    val vetId: Int,
    val date: Instant,
    val treatment: String
)
