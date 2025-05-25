package com.example.plisfunciona.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.plisfunciona.componentes.TrackItem
import com.example.plisfunciona.modelo.Track
import com.example.plisfunciona.modelo.Playlist
import com.example.plisfunciona.viewmodel.SpotifyVM

@Composable
fun PlaylistScreen(
    playlistId: String,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val viewModel: SpotifyVM = viewModel()
    val playlist by viewModel.currentPlaylist.collectAsState()

    // Cargar playlist cuando la pantalla se inicie
    LaunchedEffect(playlistId) {
        viewModel.loadPlaylistDetails(playlistId)
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header de la playlist
        playlist?.let { currentPlaylist ->
            // Imagen de la playlist
            AsyncImage(
                model = currentPlaylist.images?.firstOrNull()?.url,
                contentDescription = "Playlist cover",
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp)
            )

            // Título de la playlist
            Text(
                text = currentPlaylist.name,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            // Descripción
            currentPlaylist.description?.let { description ->
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Lista de canciones
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(currentPlaylist.tracks?.items?: emptyList()) { trackItem ->
                    TrackItem(
                        track = trackItem.track,
                        onClick = {
                            navController.navigate("player/${trackItem.track.id}")
                        }
                    )
                }
            }
        } ?: run {
            // Loading state
            CircularProgressIndicator(
                modifier = Modifier
                    .size(48.dp)
                    .padding(16.dp)
            )
        }
    }
}