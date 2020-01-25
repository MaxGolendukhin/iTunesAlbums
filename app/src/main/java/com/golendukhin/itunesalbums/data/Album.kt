package com.golendukhin.itunesalbums.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

data class AlbumsResult (val results: List<Album>)

@Parcelize
data class Album (
    val collectionId: Long,
    val collectionName: String,
    val releaseDate: String,
    val artistName: String,
    @Json(name = "artworkUrl100") val imageUrl: String
) : Parcelable