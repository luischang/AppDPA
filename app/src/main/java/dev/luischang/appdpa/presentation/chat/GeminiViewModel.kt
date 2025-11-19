package dev.luischang.appdpa.presentation.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.luischang.appdpa.data.model.Content
import dev.luischang.appdpa.data.model.GeminiRequest
import dev.luischang.appdpa.data.model.Part
import dev.luischang.appdpa.data.remote.gemini.GeminiApiService
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class GeminiViewModel: ViewModel() {

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(Interceptor { chain ->
            val request = chain.request()
            var response = chain.proceed(request)
            var tryCount = 0
            while (!response.isSuccessful && tryCount < 3) {
                tryCount++
                response.close()
                response = chain.proceed(request)
            }
            response
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://generativelanguage.googleapis.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    private val api = retrofit.create(GeminiApiService::class.java)

    var prompt by mutableStateOf("")
    var response by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    fun askGemini(apiKey: String){
        viewModelScope.launch {
            try {
                isLoading = true
                val request = GeminiRequest(
                    contents = listOf(
                        Content(
                            parts = listOf(Part(prompt))
                        )
                    )
                )
                val result = api.generateContent(apiKey, request)
                //response = result.candidates[0].content.parts[0].text
                response = result.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: "No response"


            }catch (ex: Exception){
                response = "Error: ${ex.message}"
            } finally {
                isLoading = false
            }
        }
    }
}