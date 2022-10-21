package io.baris.petclinic.dropwizard.petfact

import io.baris.petclinic.dropwizard.Util
import io.baris.petclinic.dropwizard.petfact.client.CatFactClient
import io.baris.petclinic.dropwizard.petfact.client.DogFactClient
import io.baris.petclinic.dropwizard.petfact.model.PetFactResponse

/**
 * Manages pet facts
 */
class PetFactManager(
    val dogFactClient: DogFactClient,
    val catFactClient: CatFactClient
) {
    fun petFacts(): PetFactResponse {
        Util.doWait(250)
        val catFact = catFactClient.fetchCatFact()
        val dogFact = dogFactClient.fetchDogFact()
        return PetFactResponse(catFact.fact, dogFact.facts[0])
    }
}
