package com.ayinozendy.android.poc.kmmandroidapplication.ui.playlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayinozendy.android.poc.kmmandroidapplication.R
import com.ayinozendy.android.poc.kmmandroidapplication.databinding.FragmentPlaylistBinding
import com.ayinozendy.android.poc.kmmandroidapplication.ui.video.VideoPlayerFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaylistFragment : Fragment() {
    companion object {
        const val TX_TAG = "PlaylistFragmentTag"
        fun createInstance(): PlaylistFragment {
            return PlaylistFragment()
        }
    }

    private val viewModel: PlaylistViewModel by viewModels()
    private lateinit var binding: FragmentPlaylistBinding
    private lateinit var adapter: PlaylistAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaylistBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeAdapter()
        subscribeToPlaylistLiveData()

        activity?.let { act ->
            adapter.getClickState().observe(act) { id ->
                val fragment = VideoPlayerFragment.createInstance(id)
                val tx = act.supportFragmentManager.beginTransaction()
                val tag = VideoPlayerFragment.TX_TAG
                tx.replace(R.id.fragmentContainerView, fragment, tag)
                tx.addToBackStack(tag)
                tx.commit()
            }
        }
    }

    private fun initializeAdapter() {
        adapter = PlaylistAdapter()
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.playlistRecyclerView.adapter = adapter
        binding.playlistRecyclerView.layoutManager = layoutManager
        binding.playlistRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                layoutManager.orientation
            )
        )
    }

    private fun subscribeToPlaylistLiveData() {
        viewModel.getFetchState().observe(viewLifecycleOwner) {
            when (it) {
                PlaylistViewModel.FetchStatus.Initial -> {
                    Log.e("PLAYLIST", "Initial")
                }
                PlaylistViewModel.FetchStatus.Error -> {
                    Log.e("PLAYLIST", "Loading Error")
                    viewModel.getPlaylist()
                }
                PlaylistViewModel.FetchStatus.Loading -> {
                    Log.e("PLAYLIST", "Loading Playlist")
                }
                PlaylistViewModel.FetchStatus.Success -> {
                    Log.e("PLAYLIST", "Success Playlist")
                    viewModel.getPlaylist()
                }
            }
        }

        viewModel.getPlaylistState().observe(viewLifecycleOwner) {
            adapter.setNewItemList(it.videos)
            adapter.notifyDataSetChanged()
        }

        viewModel.fetchPlaylist()
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.getFetchState().value == PlaylistViewModel.FetchStatus.Initial) {
            viewModel.getPlaylist()
        }
    }
}
