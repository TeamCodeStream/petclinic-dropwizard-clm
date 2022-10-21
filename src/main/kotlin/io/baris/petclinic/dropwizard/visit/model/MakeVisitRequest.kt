package io.baris.petclinic.dropwizard.visit.model

import java.time.Instant
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

/**
 * Represents the request for making a visit
 */
data class MakeVisitRequest(
    val date: @NotNull Instant,
    val treatment: @NotEmpty String
)
