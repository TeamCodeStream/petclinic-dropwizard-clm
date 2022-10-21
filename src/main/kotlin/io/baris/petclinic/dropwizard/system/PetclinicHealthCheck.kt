package io.baris.petclinic.dropwizard.system

import com.codahale.metrics.health.HealthCheck

/**
 * Health check for the application
 */
class PetclinicHealthCheck : HealthCheck() {
    override fun check(): Result {
        return Result.healthy()
    }
}
