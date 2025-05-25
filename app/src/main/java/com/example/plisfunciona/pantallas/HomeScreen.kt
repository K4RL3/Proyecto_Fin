package com.example.plisfunciona.pantallas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.plisfunciona.componentes.PlaylistCard
import com.example.plisfunciona.componentes.TrackItem
import com.example.plisfunciona.viewmodel.SpotifyVM
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val viewModel: SpotifyVM = viewModel()
    val playlists by viewModel.recommendedPlaylists.collectAsState()
    val recentTracks by viewModel.recentTracks.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Sección de Playlists
        Text(
            text = "Tus playlists",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        
        LazyRow {
            items(playlists) { playlist ->
                PlaylistCard(
                    playlist = playlist,
                    onClick = { navController.navigate("playlist/${playlist.id}") }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sección de Tracks Recientes
        Text(
            text = "Recientemente escuchado",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        
        LazyColumn {
            items(recentTracks) { track ->
                TrackItem(
                    track = track,
                    onClick = { navController.navigate("player/${track.id}") }
                )
            }
        }
    }
}