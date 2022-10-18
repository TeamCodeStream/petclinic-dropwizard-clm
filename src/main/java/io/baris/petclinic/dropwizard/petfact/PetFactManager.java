package io.baris.petclinic.dropwizard.petfact;

import io.baris.petclinic.dropwizard.petfact.client.CatFactClient;
import io.baris.petclinic.dropwizard.petfact.client.DogFactClient;
import io.baris.petclinic.dropwizard.petfact.model.PetFactResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;

import static io.baris.petclinic.dropwizard.Util.doWait;

/**
 * Manages pet facts
 */
@RequiredArgsConstructor
public class PetFactManager {

    private final DogFactClient dogFactClient;
    private final CatFactClient catFactClient;

    public PetFactResponse getPetFacts() {
        doWait(50);
        val catFact = catFactClient.fetchCatFact();
        val dogFact = dogFactClient.fetchDogFact();
        return new PetFactResponse(catFact.getFact(), dogFact.getFacts().get(0));
    }
}
