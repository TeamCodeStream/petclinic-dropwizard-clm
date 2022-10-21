package io.baris.petclinic.dropwizard.petfact.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.newrelic.api.agent.Trace
import io.baris.petclinic.dropwizard.Util
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class DogFactClient(
    val objectMapper: ObjectMapper,
    private val okHttpClient: OkHttpClient
) {
    val BASE_URL = "https://www.dogfactsapi.ducnguyen.dev"

    @Trace
    fun fetchDogFact(): DogFact {
        try {
            Util.doWait(250)
            val request: Request = Request.Builder()
                .url("$BASE_URL/api/v1/facts/?number=1")
                .build()

            val call: Call = okHttpClient.newCall(request)
            val response = call.execute()

            response.body?.let {
                return objectMapper.readValue(it.string(), DogFact::class.java)
            }
            throw RuntimeException("No dog")
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}
