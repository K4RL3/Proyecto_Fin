package com.example.plisfunciona.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.plisfunciona.componentes.TrackItem
import com.example.plisfunciona.modelo.Artist
import com.example.plisfunciona.modelo.Track
import com.example.plisfunciona.viewmodel.SpotifyVM
import androidx.compose.runtime.collectAsState

@Composable
fun ArtistScreen(artistId: String, navController: NavController) {
    val viewModel: SpotifyVM = viewModel()
    val artist by viewModel.artist.collectAsState()
    val topTracks by viewModel.topTracks.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        item {
            ArtistHeader(artist)
        }
        item {
            ArtistStats(artist)
        }
        item {
            Text(
                text = "Top Canciones",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }

        items(topTracks) { track ->
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            AsyncImage(
                model = it.images.firstOrNull()?.url,
                contentDescription = "Fondo de ${it.name}",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
            )
            Text(
                text = it.name,
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
private fun ArtistStats(artist: Artist?) {
    artist?.let {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem("Seguidores", "${it.followers.total}")
            if (it.popularity != null) {
                StatItem("Popularidad", "${it.popularity}%")
            }
        }
    }
}

@Composable
private fun StatItem(title: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}