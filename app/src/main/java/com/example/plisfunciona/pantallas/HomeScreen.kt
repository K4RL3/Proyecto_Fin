package com.example.plisfunciona.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.plisfunciona.componentes.PlaylistCard
import com.example.plisfunciona.componentes.TrackItem
import com.example.plisfunciona.viewmodel.SpotifyVM

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val viewModel: SpotifyVM = viewModel()
    
    // Estados básicos
    val playlists by viewModel.recommendedPlaylists.collectAsState(initial = emptyList())
    val recentTracks by viewModel.recentTracks.collectAsState(initial = emptyList())

    // UI Principal
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Sección de Playlists
        item {
            Text(
                text = "Playlists Recomendadas",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(playlists) { playlist ->
                    PlaylistCard(
                        playlist = playlist,
                        onClick = { 
                            navController.navigate("playlist/${playlist.id}")
                        }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
        
        // Sección de Tracks Recientes
        item {
            Text(
                text = "Escuchado Recientemente",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        
        items(recentTracks) { track ->
            TrackItem(
                track = track,
                onClick = { 
                    navController.navigate("player/${track.id}")
                }
            )
        }
    }
}