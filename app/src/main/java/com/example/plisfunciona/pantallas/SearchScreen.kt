package com.example.plisfunciona.pantallas

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.example.plisfunciona.componentes.TrackItem
import androidx.compose.material3.Text
import androidx.compose.runtime.setValue
import com.example.plisfunciona.modelo.SearchResponse


@Composable
fun SearchScreen(navController: NavController) {
    var query by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<SearchResponse?>(null) }

    Column {
        // Barra de búsqueda
        TextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar canciones, artistas...") }
        )

        // Resultados de búsqueda
        when {
            query.isEmpty() -> {
                Text("Busca tu música favorita")
            }
            searchResults == null -> {
                CircularProgressIndicator()
            }
            else -> {
                // Resultados de canciones
                searchResults?.tracks?.items?.let { tracks ->
                    Text("Canciones", style = MaterialTheme.typography.h6)
                    LazyColumn {
                        items(tracks) { track ->
                            TrackItem(track) {
                                navController.navigate("player/${track.id}")
                            }
                        }
                    }
                }

                // Resultados de artistas
                searchResults?.artists?.items?.let { artists ->
                    Text("Artistas", style = MaterialTheme.typography.h6)
                    LazyRow {
                        items(artists) { artist ->
                            ArtistCard(artist) {
                                navController.navigate("artist/${artist.id}")
                            }
                        }
                    }
                }
            }
        }
    }
}
