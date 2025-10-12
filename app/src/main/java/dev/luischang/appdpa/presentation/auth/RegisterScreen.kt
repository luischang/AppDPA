package dev.luischang.appdpa.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
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

    }

}