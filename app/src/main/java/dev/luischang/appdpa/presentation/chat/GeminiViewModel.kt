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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GeminiViewModel: ViewModel() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://generativelanguage.googleapis.com/")
        .addConverterFactory(GsonConverterFactory.create())
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