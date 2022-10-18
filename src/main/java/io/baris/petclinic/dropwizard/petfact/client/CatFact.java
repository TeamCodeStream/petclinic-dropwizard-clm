package io.baris.petclinic.dropwizard.petfact.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatFact {
	private String fact;
	private Integer length;
}
