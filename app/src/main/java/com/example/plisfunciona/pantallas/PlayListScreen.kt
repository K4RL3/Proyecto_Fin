package com.example.plisfunciona.pantallas

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import coil.compose.AsyncImage
import androidx.compose.material3.Text


@Composable
fun PlaylistScreen(playlistId: String, navController: NavController) {
    val playlist by viewModel.getPlaylist(playlistId).collectAsState()

    Column {
        // Header con imagen y detalles
        playlist?.let {
            AsyncImage(model = it.images.firstOrNull()?.url, contentDescription = null)
            Text(it.name, style = MaterialTheme.typography.h4)
            Text(it.description ?: "", style = MaterialTheme.typography.body1)

            // Lista de canciones
            LazyColumn {
                items(it.tracks.items) { trackItem ->
                    TrackItem(trackItem.track) {
                        navController.navigate("player/${trackItem.track.id}")
                    }
                }
            }
        } ?: CircularProgressIndicator()
    }
}