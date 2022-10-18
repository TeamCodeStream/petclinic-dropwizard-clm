package io.baris.petclinic.dropwizard.petfact.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetFactResponse {
    private String catFact;
    private String dogFact;
}
