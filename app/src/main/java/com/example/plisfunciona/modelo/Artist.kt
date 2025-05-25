package com.example.plisfunciona.modelo

data class Artist(
    val id: String,
    val name: String,
    val images: List<Image>?,
    val followers: Followers?
)

data class Followers(
    val total: Int
)