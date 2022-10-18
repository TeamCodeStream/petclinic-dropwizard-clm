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
public class DogFactClient {
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    @Trace
    public DogFact fetchDogFact() {
        try {
            doWait(50);
            HttpGet get = new HttpGet("https://www.dogfactsapi.ducnguyen.dev/api/v1/facts/?number=1");
            HttpResponse response = httpClient.execute(get);
            return objectMapper.readValue(response.getEntity().getContent(), DogFact.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
