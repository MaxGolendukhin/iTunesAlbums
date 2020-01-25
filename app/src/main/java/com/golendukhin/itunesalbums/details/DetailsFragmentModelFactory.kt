package com.golendukhin.itunesalbums.details

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.golendukhin.itunesalbums.data.Album

class DetailsViewModelFactory(private val album: Album, private val application: Application) :
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsFragmentViewModel::class.java)) {
            return DetailsFragmentViewModel(album, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}