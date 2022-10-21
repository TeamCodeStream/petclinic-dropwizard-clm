package io.baris.petclinic.dropwizard.visit.model

import java.time.Instant

/**
 * Represents a visit
 */
data class Visit(
    val id: Int,
    val petId: Int,
    val vetId: Int,
    val date: Instant,
    val treatment: String
)
