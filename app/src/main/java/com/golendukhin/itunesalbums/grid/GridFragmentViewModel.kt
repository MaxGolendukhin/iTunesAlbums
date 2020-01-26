package com.golendukhin.itunesalbums.grid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.golendukhin.itunesalbums.data.Album
import com.golendukhin.itunesalbums.network.AlbumsITunesApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE }

class GridLayoutModel : ViewModel() {
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>>
        get() = _albums

    private val _navigateToSelectedAlbum = MutableLiveData<Album>()
    val navigateToSelectedAlbum: LiveData<Album>
        get() = _navigateToSelectedAlbum

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val searchTextMutableLiveData = MutableLiveData<String>()

    fun getAlbums(searchedAlbum: String) {
        coroutineScope.launch {
            val getAlbumsDeferred = AlbumsITunesApi.retrofitService.getAlbumsAsync(searchedAlbum.replace(" ", "+"), "album")
            try {
                _status.value = ApiStatus.LOADING
                val listResult = getAlbumsDeferred.await()
                _status.value = ApiStatus.DONE
                _albums.value = listResult.results.sortedBy { it.collectionName }
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _albums.value = ArrayList()
            }
        }
    }

    fun displayAlbumDetails(album: Album) {
        _navigateToSelectedAlbum.value = album
    }

    fun displayAlbumDetailsCompleted() {
        _navigateToSelectedAlbum.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}