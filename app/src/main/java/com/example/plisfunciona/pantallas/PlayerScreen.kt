package com.example.plisfunciona.pantallas

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage



@Composable
fun PlayerScreen(trackId: String) {
    val track by viewModel.getTrack(trackId).collectAsState()
    var isPlaying by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        track?.let {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Portada del álbum
                AsyncImage(
                    model = it.album.images.firstOrNull()?.url,
                    contentDescription = null,
                    modifier = Modifier.size(300.dp)
                )

                // Información de la canción
                Text(it.name, style = MaterialTheme.typography.titleMedium)
                Text(it.artists.joinToString { a -> a.name }, style = MaterialTheme.typography.bodyMedium)

                // Controles del reproductor
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { /* Anterior */ }) {
                        Icon(Icons.Default.SkipPrevious, null)
                    }

                    IconButton(onClick = { isPlaying = !isPlaying }) {
                        Icon(if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow, null)
                    }

                    IconButton(onClick = { /* Siguiente */ }) {
                        Icon(Icons.Default.SkipNext, null)
                    }
                }
            }
        } ?: CircularProgressIndicator()
    }
}