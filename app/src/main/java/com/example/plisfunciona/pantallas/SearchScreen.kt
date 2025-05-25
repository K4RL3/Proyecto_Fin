package com.example.plisfunciona.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.plisfunciona.componentes.TrackItem
import com.example.plisfunciona.componentes.ArtistCard
import com.example.plisfunciona.viewmodel.SpotifyVM

@Composable
fun SearchScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val viewModel: SpotifyVM = viewModel()
    var searchQuery by remember { mutableStateOf("") }
    val searchResults by viewModel.searchResults.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Barra de búsqueda
        TextField(
            value = searchQuery,
            onValueChange = { 
                searchQuery = it
                viewModel.searchContent(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            placeholder = { Text("Buscar canciones, artistas...") },
            singleLine = true
        )

        // Resultados de búsqueda
        LazyColumn {
            // Sección de canciones
            item {
                if (searchResults.tracks.isNotEmpty()) {
                    Text(
                        text = "Canciones",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
            
            items(searchResults.tracks) { track ->
                TrackItem(
                    track = track,
                    onClick = { navController.navigate("player/${track.id}") }
                )
            }

            // Sección de artistas
            item {
                if (searchResults.artists.isNotEmpty()) {
                    Text(
                        text = "Artistas",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }

            items(searchResults.artists) { artist ->
                ArtistCard(
                    artist = artist,
                    onClick = { navController.navigate("artist/${artist.id}") }
                )
            }
        }
    }
}
