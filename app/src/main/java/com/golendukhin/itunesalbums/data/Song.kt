package com.golendukhin.itunesalbums.data

data class SongsResult (val results: List<Song>)

data class Song(val trackName: String?, val trackNumber: Int?) {
    fun getTrackNumber(): String {
        return "${trackNumber.toString()}."
    }
}