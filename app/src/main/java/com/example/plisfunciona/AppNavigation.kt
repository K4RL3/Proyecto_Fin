package com.example.plisfunciona

import com.example.plisfunciona.pantallas.ArtistScreen
import com.example.plisfunciona.pantallas.HomeScreen
import com.example.plisfunciona.pantallas.PlayerScreen
import com.example.plisfunciona.pantallas.PlaylistScreen
import com.example.plisfunciona.pantallas.SearchScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(navController)
        }
        composable("search") {
            SearchScreen(navController)
        }
        composable("playlist/{id}") { backStackEntry ->
            PlaylistScreen(
                playlistId = backStackEntry.arguments?.getString("id") ?: "",
                navController = navController
            )
        }
        composable("artist/{id}") { backStackEntry ->
            ArtistScreen(
                artistId = backStackEntry.arguments?.getString("id") ?: "",
                navController = navController
            )
        }
        composable("player/{id}") { backStackEntry ->
            PlayerScreen(
                trackId = backStackEntry.arguments?.getString("id") ?: ""
            )
        }
    }
}