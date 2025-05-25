package com.example.plisfunciona.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.plisfunciona.viewmodel.SpotifyVM

@Composable
fun PlayerScreen(
    trackId: String,
    modifier: Modifier = Modifier
) {
    val viewModel: SpotifyVM = viewModel()
    var isPlaying by remember { mutableStateOf(false) }
    val track by viewModel.currentTrack.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Portada del álbum
        AsyncImage(
            model = track?.album?.images?.firstOrNull()?.url,
            contentDescription = "Portada del álbum",
            modifier = Modifier
                .size(250.dp)
                .padding(16.dp)
        )

        // Información de la canción
        Text(
            text = track?.name ?: "Cargando...",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = track?.artists?.joinToString { it.name } ?: "",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Botón simple en lugar de IconButton
        Button(
            onClick = { isPlaying = !isPlaying },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = if (isPlaying) "Pausar" else "Reproducir")
        }
    }
}