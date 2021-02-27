package com.ayinozendy.android.poc.kmmandroidapplication.ui.playlist

import androidx.recyclerview.widget.RecyclerView
import com.ayinozendy.android.poc.kmm.shared.model.Video
import com.ayinozendy.android.poc.kmmandroidapplication.databinding.ListItemPlaylistBinding
import com.squareup.picasso.Picasso

class PlaylistItemViewHolder(private val viewBinding: ListItemPlaylistBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    lateinit var item: Video

    fun bind(item: Video) {
        this.item = item
        viewBinding.titleTextView.text = item.title
        viewBinding.authorTextView.text = item.author
        viewBinding.descriptionTextView.text = item.description
        if (item.thumbnailUrl.isNotBlank()) {
            Picasso.get().load(item.thumbnailUrl).into(viewBinding.thumbnailImageView)
        }
    }
}
