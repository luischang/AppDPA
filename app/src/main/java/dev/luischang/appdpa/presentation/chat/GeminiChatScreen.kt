package dev.luischang.appdpa.presentation.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GeminiChatScreen(apiKey: String, viewModel: GeminiViewModel = viewModel()){
    Column(modifier = Modifier.fillMaxSize().padding(16.dp))
    {
        OutlinedTextField(
            value = viewModel.prompt,
            onValueChange = { viewModel.prompt = it },
            label = { Text("Haz tu Pregunta") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = {viewModel.askGemini(apiKey)},
            enabled = !viewModel.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Preguntar")
        }
        Spacer(modifier = Modifier.padding(8.dp))
        if(viewModel.isLoading){
            CircularProgressIndicator()
        } else {
            Text("Respuesta: ", style = MaterialTheme.typography.bodyMedium)
            Text(text = viewModel.response, style = MaterialTheme.typography.bodyMedium)
        }
    }
}