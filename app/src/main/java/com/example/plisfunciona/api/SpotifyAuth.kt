package com.example.plisfunciona.api

object SpotifyAuth {
    const val CLIENT_ID = "3126ad31559446c088af30b9b4f59801"
    const val CLIENT_SECRET = "2fbe626dc4fd47a29d36fa220949447f"
    const val REDIRECT_URI = "com.example.canciones://callback"

    const val AUTH_URL = "https://accounts.spotify.com/authorize"
    const val TOKEN_URL = "https://accounts.spotify.com/api/token"

    fun getAuthRequest(clientId: String): String {
        return "$AUTH_URL?client_id=$clientId&response_type=code&redirect_uri=$REDIRECT_URI"
    }
}