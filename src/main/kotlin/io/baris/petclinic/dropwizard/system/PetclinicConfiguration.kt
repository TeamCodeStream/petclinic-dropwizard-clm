package io.baris.petclinic.dropwizard.system

import com.fasterxml.jackson.annotation.JsonCreator
import io.dropwizard.Configuration
import io.dropwizard.db.DataSourceFactory

/**
 * Pet Clinic service configuration
 */
data class PetclinicConfiguration @JsonCreator constructor(
    val env: String,
    val database: DataSourceFactory,
    val databaseConfig: DatabaseConfig
) : Configuration()
