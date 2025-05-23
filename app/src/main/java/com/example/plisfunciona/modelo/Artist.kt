package com.example.plisfunciona.modelo

import android.media.Image

data class Artist(
    val id: String,
    val name: String,
    val images: List<Image>,
    val followers: Followers?
)

data class Followers(
    val total: Int
)