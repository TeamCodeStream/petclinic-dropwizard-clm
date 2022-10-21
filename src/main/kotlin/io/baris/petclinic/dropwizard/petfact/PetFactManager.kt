package io.baris.petclinic.dropwizard.petfact

import io.baris.petclinic.dropwizard.Util
import io.baris.petclinic.dropwizard.petfact.client.CatFact
import io.baris.petclinic.dropwizard.petfact.client.DogFactClient
import io.baris.petclinic.dropwizard.petfact.client.fetch
import io.baris.petclinic.dropwizard.petfact.client.fetchDogFact
import io.baris.petclinic.dropwizard.petfact.model.PetFactResponse

/**
 * Manages pet facts
 */
class PetFactManager(
    val dogFactClient: DogFactClient
) {
    fun petFacts(): PetFactResponse {
        Util.doWait(250)
        val catFact = CatFact.fetch()
        val dogFact = dogFactClient.fetchDogFact()
        return PetFactResponse(catFact?.fact, dogFact.facts[0])
    }
}
