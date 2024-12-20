package com.practicum.playlistmaker.media.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.media.ui.model.Playlist


class PlaylistAdapter(
    private val playlists: List<Playlist>,
    private val onItemClickListener: OnItemClickListener,
) :
        RecyclerView.Adapter<PlaylistViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.playlist_item, parent, false)
            return PlaylistViewHolder(view)
        }

        override fun getItemCount(): Int {
            return playlists.size
        }

        override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
            holder.bind(playlists[position])
            holder.itemView.setOnClickListener { onItemClickListener.onItemClick(playlists[position]) }
        }
    }

    fun interface OnItemClickListener {
    fun onItemClick(item: Playlist)
}