package io.baris.petclinic.dropwizard.system

data class DatabaseConfig(
    val name: String,
    val dockerImage: String,
    val initScript: String
)
