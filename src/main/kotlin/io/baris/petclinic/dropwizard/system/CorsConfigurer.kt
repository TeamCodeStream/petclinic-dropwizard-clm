package io.baris.petclinic.dropwizard.system

import io.dropwizard.setup.Environment
import org.eclipse.jetty.servlets.CrossOriginFilter
import java.util.*
import javax.servlet.DispatcherType

/**
 * Configures CORS
 */
object CorsConfigurer {
    fun configureCors(environment: Environment) {
        // CORS headers
        val cors = environment.servlets()
            .addFilter("CORS", CrossOriginFilter::class.java)

        // Configure CORS parameters
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*")
        cors.setInitParameter(
            CrossOriginFilter.ALLOWED_HEADERS_PARAM,
            "Authorization,X-Requested-With,Content-Type,Accept,Origin"
        )
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD")

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType::class.java), true, "/*")
    }
}
