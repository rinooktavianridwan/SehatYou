package com.example.sehatyou.GroqAI

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GroqApiClient {
    private const val BASE_URL = "https://api.groq.com/"

    val api: GroqCloudApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GroqCloudApi::class.java)
    }
}
