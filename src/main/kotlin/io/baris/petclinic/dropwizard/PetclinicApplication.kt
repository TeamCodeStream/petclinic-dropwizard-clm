package io.baris.petclinic.dropwizard

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.baris.petclinic.dropwizard.homepage.HomepageResource
import io.baris.petclinic.dropwizard.pet.PetManager
import io.baris.petclinic.dropwizard.pet.PetResource
import io.baris.petclinic.dropwizard.petfact.DolphinFactResource
import io.baris.petclinic.dropwizard.petfact.PetFactManager
import io.baris.petclinic.dropwizard.petfact.PetFactResource
import io.baris.petclinic.dropwizard.petfact.client.CatFactClient
import io.baris.petclinic.dropwizard.petfact.client.DogFactClient
import io.baris.petclinic.dropwizard.system.CorsConfigurer
import io.baris.petclinic.dropwizard.system.PetclinicConfiguration
import io.baris.petclinic.dropwizard.system.PetclinicHealthCheck
import io.baris.petclinic.dropwizard.system.PostgreUtils
import io.baris.petclinic.dropwizard.vet.VetManager
import io.baris.petclinic.dropwizard.vet.VetResource
import io.baris.petclinic.dropwizard.visit.VisitManager
import io.baris.petclinic.dropwizard.visit.VisitResource
import io.dropwizard.Application
import io.dropwizard.jdbi3.JdbiFactory
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource
import okhttp3.OkHttpClient
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin

/**
 * Vet service application class to bootstrap the application
 */
class PetclinicApplication : Application<PetclinicConfiguration>() {
    private lateinit var objectMapper: ObjectMapper

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            PetclinicApplication().run(*args)
        }

        var catFactClient: CatFactClient? = null
    }

    override fun getName(): String {
        return "Pet Clinic"
    }

    override fun initialize(bootstrap: Bootstrap<PetclinicConfiguration>) {
        objectMapper = bootstrap.objectMapper
        objectMapper.registerModule(KotlinModule())
    }

    override fun run(
        configuration: PetclinicConfiguration,
        environment: Environment
    ) {
        environment.healthChecks().register("health", PetclinicHealthCheck())
        initialiseBeans(configuration, environment)
        CorsConfigurer.configureCors(environment)
    }

    private fun initialiseBeans(
        configuration: PetclinicConfiguration,
        environment: Environment
    ) {
        val jdbi = JdbiFactory()
            .build(environment, configuration.database, configuration.databaseConfig.name)
        jdbi.installPlugin(SqlObjectPlugin())
        jdbi.installPlugin(KotlinPlugin())
        jdbi.installPlugin(KotlinSqlObjectPlugin())

        // initialize DB schema
        PostgreUtils.applySqlScript(jdbi, configuration.databaseConfig.initScript)

        val vetManager = VetManager(jdbi)
        val petManager = PetManager(jdbi)
        val visitManager = VisitManager(jdbi)

        val client: OkHttpClient = OkHttpClient.Builder().build()

        val catFactClient = CatFactClient(objectMapper, client)
        PetclinicApplication.catFactClient = catFactClient
        val dogFactClient = DogFactClient(objectMapper, client)
        val petFactManager = PetFactManager(dogFactClient)

        // register resources
        environment.jersey().register(DolphinFactResource())
        environment.jersey().register(PetFactResource(petFactManager))
        environment.jersey().register(VetResource(vetManager))
        environment.jersey().register(PetResource(petManager))
        environment.jersey().register(VisitResource(visitManager, petManager, vetManager))
        environment.jersey().register(HomepageResource())
        environment.jersey().register(OpenApiResource())
    }
}
