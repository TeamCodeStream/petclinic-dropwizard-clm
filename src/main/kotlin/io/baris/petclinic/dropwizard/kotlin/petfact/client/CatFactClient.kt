package io.baris.petclinic.dropwizard.kotlin.petfact.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.newrelic.api.agent.Trace
import io.baris.petclinic.dropwizard.Util
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import java.io.IOException

class CatFactClient(
        val objectMapper: ObjectMapper,
        val httpClient: HttpClient,
) {
    @Trace
    fun fetchCatFact(): CatFact {
        Util.doWait(250)
        return try {
            val get = HttpGet("https://catfact.ninja/fact")
            val response = httpClient.execute(get)
            objectMapper.readValue(response.entity.content, CatFact::class.java)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}
