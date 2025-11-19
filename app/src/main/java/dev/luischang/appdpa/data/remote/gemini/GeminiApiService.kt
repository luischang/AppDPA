package dev.luischang.appdpa.data.remote.gemini

import dev.luischang.appdpa.data.model.GeminiRequest
import dev.luischang.appdpa.data.model.GeminiResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface GeminiApiService {

    @POST("v1beta/models/gemini-2.5-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): GeminiResponse

}