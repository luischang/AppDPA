package dev.luischang.appdpa.presentation.auth

import android.webkit.WebView
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import dev.luischang.appdpa.data.remote.firebase.FirebaseAuthManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavController){

    var name by remember {mutableStateOf("")}
    var email by remember {mutableStateOf("")}
    var password by remember {mutableStateOf("")}
    var confirmPassword by remember {mutableStateOf("")}

    var acceptTerms by remember {mutableStateOf(false)}
    var showTerms by remember {mutableStateOf(false)}


    val context = LocalContext.current

    Column(
        modifier = Modifier.padding(16.dp)
    ){
        Spacer(modifier = Modifier.padding(16.dp))
        Text("Registro de Usuario", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()
        )
        //OutlinedTextField for email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()
        )
        //OutlinedTextField for password
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        //OutlinedTextField for confirm password
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar contraseña") },
            modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically){
            Checkbox(
                checked = acceptTerms,
                onCheckedChange = { acceptTerms = it }
            )
            Button(onClick = { showTerms = true }){
                Text("Acepto los términos y condiciones")
            }
        }


        //Button for register
        Button(
            onClick = {
                if(name.isNotBlank()
                    && password.isNotBlank()
                    && password == confirmPassword){

                    CoroutineScope(Dispatchers.Main).launch {
                        val result = FirebaseAuthManager.registerUser(name, email, password)
                        if(result.isSuccess){
                            navController.navigate("login")
                        } else {
                            val error = result.exceptionOrNull()?.message ?: "Error desconocido"
                            Toast.makeText(context
                                ,error
                                , Toast.LENGTH_LONG).show()
                        }
                    }

                }
            },
            modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()
        ) {
            Text("Registrar")
        }

        if(showTerms){
            AlertDialog(
                onDismissRequest = { showTerms = false },
                confirmButton =  {
                    TextButton(onClick = { showTerms = false }) {
                        Text("Cerrar")
                    }
                },
                title = { Text("Términos y condiciones") },
                text = {
                    //WebView
                    AndroidView(
                        factory = { context ->
                            WebView(context).apply {
                                settings.javaScriptEnabled = true
                                settings.domStorageEnabled = true
                                loadUrl("https://www.privacypolicies.com/live/23251f1a-b7d8-45e9-9485-5ea09c783c85")
                            }
                        },
                        modifier = Modifier.height(300.dp)
                    )
                }
            )
        }

    }

}