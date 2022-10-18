package io.baris.petclinic.dropwizard;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.baris.petclinic.dropwizard.homepage.HomepageResource;
import io.baris.petclinic.dropwizard.pet.PetManager;
import io.baris.petclinic.dropwizard.pet.PetResource;
import io.baris.petclinic.dropwizard.petfact.PetFactManager;
import io.baris.petclinic.dropwizard.petfact.PetFactResource;
import io.baris.petclinic.dropwizard.petfact.client.CatFactClient;
import io.baris.petclinic.dropwizard.petfact.client.DogFactClient;
import io.baris.petclinic.dropwizard.system.PetclinicConfiguration;
import io.baris.petclinic.dropwizard.system.PetclinicHealthCheck;
import io.baris.petclinic.dropwizard.vet.VetManager;
import io.baris.petclinic.dropwizard.vet.VetResource;
import io.baris.petclinic.dropwizard.visit.VisitManager;
import io.baris.petclinic.dropwizard.visit.VisitResource;
import io.dropwizard.Application;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import lombok.val;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import static io.baris.petclinic.dropwizard.system.CorsConfigurer.configureCors;
import static io.baris.petclinic.dropwizard.system.PostgreUtils.applySqlScript;

/**
 * Vet service application class to bootstrap the application
 */
public class PetclinicApplication extends Application<PetclinicConfiguration> {
    private ObjectMapper objectMapper;

    public static void main(final String[] args) throws Exception {
        new PetclinicApplication().run(args);
    }

    @Override
    public String getName() {
        return "Pet Clinic";
    }

    @Override
    public void initialize(final Bootstrap<PetclinicConfiguration> bootstrap) {
        this.objectMapper = bootstrap.getObjectMapper();
    }

    @Override
    public void run(
        final PetclinicConfiguration configuration,
        final Environment environment
    ) {
        environment.healthChecks().register("health", new PetclinicHealthCheck());

        initialiseBeans(configuration, environment);

        configureCors(environment);
    }

    private void initialiseBeans(
        final PetclinicConfiguration configuration,
        final Environment environment
    ) {
        var jdbi = new JdbiFactory()
            .build(environment, configuration.getDatabase(), configuration.getDatabaseConfig().getName());
        jdbi.installPlugin(new SqlObjectPlugin());

        // initialize DB schema
        applySqlScript(jdbi, configuration.getDatabaseConfig().getInitScript());

        // Init http client
        val httpClient = new HttpClientBuilder(environment).using(configuration.getHttpClientConfiguration())
                .build(getName());
        val catFactClient = new CatFactClient(objectMapper, httpClient);
        environment.jersey().register(catFactClient);
        val dogFactClient = new DogFactClient(objectMapper, httpClient);
        environment.jersey().register(dogFactClient);

        val vetManager = new VetManager(jdbi);
        val petManager = new PetManager(jdbi);
        val visitManager = new VisitManager(jdbi);
        val petFactManager = new PetFactManager(dogFactClient, catFactClient);

        // register resources
        environment.jersey().register(new PetFactResource(petFactManager));
        environment.jersey().register(new VetResource(vetManager));
        environment.jersey().register(new PetResource(petManager));
        environment.jersey().register(new VisitResource(visitManager, petManager, vetManager));
        environment.jersey().register(new HomepageResource());
        environment.jersey().register(new OpenApiResource());
    }
}
