package com.example.plisfunciona.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.plisfunciona.componentes.TrackItem
import com.example.plisfunciona.modelo.Artist
import com.example.plisfunciona.modelo.Track
import com.example.plisfunciona.viewmodel.SpotifyVM
import androidx.compose.runtime.collectAsState

@Composable
fun ArtistScreen(
    artistId: String,
    navController: NavController
) {
    val viewModel: SpotifyVM = viewModel()

    // Cargar datos del artista y sus canciones top
    LaunchedEffect(artistId) {
        viewModel.loadArtist(artistId)
        viewModel.loadArtistTopTracks(artistId)
    }

    val artist by viewModel.currentArtist.collectAsState()
    val tracks by viewModel.artisrTopTracks.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            ArtistHeader(artist)
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Text(
                text = "Top Canciones",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        items(tracks) { track ->
            TrackItem(
                track = track,
                onClick = { navController.navigate("player/${track.id}") }
            )
        }
    }
}

@Composable
private fun ArtistHeader(artist: Artist?) {
    artist?.let {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = it.images?.firstOrNull()?.url,
                contentDescription = "Foto de ${it.name}",
                modifier = Modifier
                    .size(200.dp)
                    .padding(8.dp)
            )
            Text(
                text = it.name,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = "${it.followers?.total ?: 0} seguidores",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}