package com.ayinozendy.android.poc.kmmandroidapplication.ui.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayinozendy.android.poc.kmm.shared.data.repository.video.VideoRepository
import com.ayinozendy.android.poc.kmm.shared.model.Video
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    private val videoRepository: VideoRepository
) : ViewModel() {
    sealed class FetchStatus {
        object Initial : FetchStatus()
        object Loading : FetchStatus()
        data class Success(
            val video: Video?
        ) : FetchStatus()

        object Error : FetchStatus()
    }

    private val videoState = MutableLiveData<FetchStatus>(FetchStatus.Initial)

    fun getVideoState(): LiveData<FetchStatus> = videoState

    fun getVideo(id: Int) {
        videoState.postValue(FetchStatus.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val video = videoRepository.getVideo(id)
                videoState.postValue(FetchStatus.Success(video))
            } catch (e: Exception) {
                videoState.postValue(FetchStatus.Error)
            }
        }
    }
}
