package com.example.plisfunciona.modelo

data class TracksResponse(
    val items: List<PlayHistoryObject>
)

data class PlayHistoryObject(
    val track: Track,
    val played_at: String
)

data class Track(
    val id: String,
    val name: String,
    val artist: String,
    val album: String,
    val duration: Int,
    val explicit: Boolean,
    val external_urls: ExternalUrls
)

data class ExternalUrls(
    val spotify: String
)

