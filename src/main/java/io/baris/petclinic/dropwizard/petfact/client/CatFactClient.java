package io.baris.petclinic.dropwizard.petfact.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newrelic.api.agent.Trace;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;

import static io.baris.petclinic.dropwizard.Util.doWait;

@RequiredArgsConstructor
public class CatFactClient {
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    @Trace
    public CatFact fetchCatFact() {
        doWait(50);
        try {
            HttpGet get = new HttpGet("https://catfact.ninja/fact");
            HttpResponse response = httpClient.execute(get);
            return objectMapper.readValue(response.getEntity().getContent(), CatFact.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
