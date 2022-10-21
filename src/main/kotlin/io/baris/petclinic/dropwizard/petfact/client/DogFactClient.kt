package io.baris.petclinic.dropwizard.petfact.client

import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient

class DogFactClient(
    val objectMapper: ObjectMapper,
    val okHttpClient: OkHttpClient
) {
    val BASE_URL = "https://www.dogfactsapi.ducnguyen.dev"
}
