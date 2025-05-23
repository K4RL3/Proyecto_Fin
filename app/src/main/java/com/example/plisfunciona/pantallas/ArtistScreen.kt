package com.example.plisfunciona.pantallas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.plisfunciona.componentes.TrackItem
import com.example.plisfunciona.viewmodel.SpotifyVM

@Composable
fun ArtistScreen(artistId: String, navController: NavController) {
    val viewModel: SpotifyVM = viewModel()
    val artist by viewModel.getArtist(artistId).collectAsState()
    val topTracks by viewModel.getArtistTopTracks(artistId).collectAsState()

    Column {
        artist?.let {
            // Header del artista
            AsyncImage(model = it.images.firstOrNull()?.url, contentDescription = null)
            Text(it.name, style = typography.titleMedium)
            Text("${it.followers.total} seguidores", style = typography.bodySmall)

            // Top canciones
            Text("Top canciones", style = typography.titleLarge)
            LazyColumn {
                items(topTracks) { track ->
                    TrackItem(track) {
                        navController.navigate("player/${track.id}")
                    }
                }
            }
        } ?: CircularProgressIndicator()
    }
}