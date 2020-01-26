package com.golendukhin.itunesalbums.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.golendukhin.itunesalbums.data.Album
import com.golendukhin.itunesalbums.data.Song
import com.golendukhin.itunesalbums.network.SongsITunesApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailsFragmentViewModel(val album: Album, application: Application) : AndroidViewModel(application)  {
    private val _songs = MutableLiveData<List<Song>>()
    val songs: LiveData<List<Song>>
        get() = _songs

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        coroutineScope.launch {
            val getSongsDeferred = SongsITunesApi.retrofitService.getSongsAsync(album.collectionId.toString(), "song")
            try {
                val listResult = getSongsDeferred.await()
                _songs.value = listResult.results.drop(1)
            } catch (e: Exception) {
                _songs.value = ArrayList()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}