package io.baris.petclinic.dropwizard.petfact.client

import com.newrelic.api.agent.Trace
import io.baris.petclinic.dropwizard.PetclinicApplication
import io.baris.petclinic.dropwizard.Util.doWait
import okhttp3.Call
import okhttp3.Request

@Trace
fun CatFact.Companion.fetch(): CatFact? {
    doWait(350)
    return PetclinicApplication.catFactClient?.fetchCatFact()
}

@Trace
fun DogFactClient.fetchDogFact(): DogFact {
    doWait(250)
    val request: Request = Request.Builder()
        .url("$BASE_URL/api/v1/facts/?number=1")
        .build()

    val call: Call = this.okHttpClient.newCall(request)
    val response = call.execute()

    response.body?.let {
        return objectMapper.readValue(it.string(), DogFact::class.java)
    }
    throw RuntimeException("No dog")
}
