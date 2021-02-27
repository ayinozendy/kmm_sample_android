package com.ayinozendy.android.poc.kmmandroidapplication.ui.video

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ayinozendy.android.poc.kmm.shared.model.Video
import com.ayinozendy.android.poc.kmmandroidapplication.databinding.FragmentVideoViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoPlayerFragment : Fragment() {
    companion object {
        const val TX_TAG = "VideoPlayerFragment"
        private const val VIDEO_ID_BUNDLE = "video_id_bundle"

        fun createInstance(videoId: Int): VideoPlayerFragment {
            val bundle = Bundle()
            bundle.putInt(VIDEO_ID_BUNDLE, videoId)
            return VideoPlayerFragment().apply {
                arguments = bundle
            }
        }
    }

    private lateinit var binding: FragmentVideoViewBinding
    private val viewModel: VideoPlayerViewModel by viewModels()
    private lateinit var mediaController: MediaController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoViewBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToVideoLiveData()

        val id = arguments?.getInt(VIDEO_ID_BUNDLE)
        id?.let {
            viewModel.getVideo(id)
        }
    }

    private fun subscribeToVideoLiveData() {
        viewModel.getVideoState().observe(viewLifecycleOwner) {
            when (it) {
                VideoPlayerViewModel.FetchStatus.Error -> showError()
                VideoPlayerViewModel.FetchStatus.Initial -> showInitial()
                VideoPlayerViewModel.FetchStatus.Loading -> showLoading()
                is VideoPlayerViewModel.FetchStatus.Success -> showVideo(it.video)
            }
        }
    }

    private fun showError() {
        Log.e("PLAYLIST", "Loading Error")
    }

    private fun showInitial() {
        Log.e("PLAYLIST", "Initial")
    }

    private fun showLoading() {
        Log.e("PLAYLIST", "Loading")
    }

    private fun showVideo(video: Video?) {
        Log.e("PLAYLIST", "Success Playlist")
        video?.let {
            binding.titleTextView.text = video.title
            binding.authorTextView.text = video.author
            binding.videoDescriptionTextView.text = video.description

            activity?.let {
                mediaController = MediaController(requireActivity())
                mediaController.setAnchorView(binding.videoPlayerView)
                binding.videoPlayerView.setVideoPath(video.videoUrl)
                binding.videoPlayerView.setMediaController(mediaController)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        binding.videoPlayerView.pause()
    }
}
