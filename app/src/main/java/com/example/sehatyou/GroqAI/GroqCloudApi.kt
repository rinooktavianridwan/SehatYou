package com.example.sehatyou.GroqAI

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface GroqCloudApi {
    @Headers("Authorization: Bearer YOUR_API_KEY") // Ganti dengan API key Anda
    @POST("openai/v1/chat/completions")
    suspend fun getChatResponse(@Body request: ChatRequest?): ChatResponse?
}


