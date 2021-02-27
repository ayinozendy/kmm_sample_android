package com.ayinozendy.android.poc.kmmandroidapplication.ui.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayinozendy.android.poc.kmm.shared.data.repository.playlist.PlaylistRepository
import com.ayinozendy.android.poc.kmm.shared.model.Playlist
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val playlistRepository: PlaylistRepository
) : ViewModel() {

    sealed class FetchStatus {
        object Initial : FetchStatus()
        object Loading : FetchStatus()
        object Success : FetchStatus()
        object Error : FetchStatus()
    }

    private val fetchStatus = MutableLiveData<FetchStatus>(FetchStatus.Initial)
    private val playlistState = MutableLiveData<Playlist>()

    fun getFetchState(): LiveData<FetchStatus> = fetchStatus
    fun getPlaylistState(): LiveData<Playlist> = playlistState

    fun fetchPlaylist() {
        fetchStatus.postValue(FetchStatus.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                playlistRepository.fetchPlaylist()
                fetchStatus.postValue(FetchStatus.Success)
            } catch (e: Exception) {
                fetchStatus.postValue(FetchStatus.Error)
            }
        }
    }

    fun getPlaylist() {
        viewModelScope.launch(Dispatchers.IO) {
            val playlist = playlistRepository.getPlaylist()
            playlistState.postValue(playlist)
        }
    }
}
