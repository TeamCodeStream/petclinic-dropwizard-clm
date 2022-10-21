package io.baris.petclinic.dropwizard.petfact.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.newrelic.api.agent.Trace
import io.baris.petclinic.dropwizard.Util
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class CatFactClient(
    private val objectMapper: ObjectMapper,
    private val okHttpClient: OkHttpClient
) {
    val BASE_URL = "https://catfact.ninja"

    @Trace
    fun fetchCatFact(): CatFact {
        Util.doWait(250)
        try {
            val request: Request = Request.Builder()
                .url("$BASE_URL/fact")
                .build()

            val call: Call = okHttpClient.newCall(request)
            val response = call.execute()

            response.body?.let {
                return objectMapper.readValue(it.string(), CatFact::class.java)
            }
            throw RuntimeException("No cat")
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}
