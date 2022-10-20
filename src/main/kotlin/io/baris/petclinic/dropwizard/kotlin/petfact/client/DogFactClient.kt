package io.baris.petclinic.dropwizard.kotlin.petfact.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.newrelic.api.agent.Trace
import io.baris.petclinic.dropwizard.Util
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import java.io.IOException

class DogFactClient(
        val objectMapper: ObjectMapper,
        val httpClient: HttpClient,
) {
    @Trace
    fun fetchDogFact(): DogFact {
        return try {
            Util.doWait(250)
            val get = HttpGet("https://www.dogfactsapi.ducnguyen.dev/api/v1/facts/?number=1")
            val response = httpClient.execute(get)
            objectMapper.readValue(response.entity.content, DogFact::class.java)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}