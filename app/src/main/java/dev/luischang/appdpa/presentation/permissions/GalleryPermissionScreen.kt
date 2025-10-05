package dev.luischang.appdpa.presentation.permissions

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import coil.compose.rememberAsyncImagePainter


@Composable
fun GalleryPermissionScreen() {
    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var permissionGranted by remember { mutableStateOf(false) }

    // Galería launcher
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) selectedImageUri = uri
    }

    // Permiso launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        permissionGranted = granted
        if (granted) {
            pickImageLauncher.launch("image/*")
        }
    }

    val permission =
        Manifest.permission.READ_MEDIA_IMAGES

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Galería de Fotos", style = MaterialTheme.typography.titleLarge)

        Button(onClick = {
            val isGranted = ContextCompat.checkSelfPermission(context, permission) ==
                    PermissionChecker.PERMISSION_GRANTED

            if (isGranted) {
                permissionGranted = true
                pickImageLauncher.launch("image/*")
            } else {
                permissionLauncher.launch(permission)
            }
        }) {
            Text("Seleccionar imagen de la galería")
        }

        selectedImageUri?.let {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = "Imagen seleccionada",
                modifier = Modifier
                    .size(250.dp)
                    .padding(8.dp)
            )
        }
    }
}