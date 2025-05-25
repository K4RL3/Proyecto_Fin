package com.example.plisfunciona.modelo

data class TracksResponse(
    val tracks: List<Track>
)

data class PlayHistoryObject(
    val track: Track,
    val played_at: String
)

data class ExternalUrls(
    val spotify: String
)

