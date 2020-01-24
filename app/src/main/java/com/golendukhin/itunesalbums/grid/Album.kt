package com.golendukhin.itunesalbums.grid

import com.squareup.moshi.Json

data class Results (
    val results: List<Album>
)

data class Album (
    val collectionId: Long,
    @Json(name = "artworkUrl100") val imageUrl: String
)