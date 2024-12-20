package com.practicum.playlistmaker.player.ui

import android.os.Environment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.media.ui.model.Playlist
import com.practicum.playlistmaker.utils.convertDpToPx
import com.practicum.playlistmaker.utils.getDefaultCacheImagePath
import java.io.File

class PlaylistHorizontalViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val cover: ImageView = itemView.findViewById(R.id.playlist_cover)
    private val name: TextView = itemView.findViewById(R.id.playlist_name)
    private val numTracks: TextView = itemView.findViewById(R.id.playlist_tracks)

    fun bind(playlist: Playlist) {
        if (!playlist.imagePath.isNullOrEmpty()) {
            cover.scaleType = ImageView.ScaleType.CENTER_CROP
            val filePath =
                getDefaultCacheImagePath(itemView.context)
            val file = File(filePath, playlist.imagePath)
            Glide.with(itemView)
                .load(file.toUri())
                .placeholder(R.drawable.ic_placeholder)
                .apply(
                    RequestOptions().transform(
                        MultiTransformation(
                            CenterCrop(),
                            RoundedCorners(
                                convertDpToPx(
                                    2f,
                                    itemView.context
                                )
                            )
                        )
                    )
                )
                .into(cover)
        } else {
            cover.setImageDrawable(getDrawable(itemView.context, R.drawable.ic_placeholder))
        }
        name.text = playlist.name
        playlist.tracks.size.let {
            numTracks.text = getPluralForm(it).format(it)
        }
    }

    private fun getPluralForm(num: Int): String {
        val n = num % 100
        return when {
            n in 11..14 -> itemView.context.getString(R.string.trackss)
            n % 10 == 1 -> itemView.context.getString(R.string.track)
            n % 10 in 2..4 -> itemView.context.getString(R.string.tracks)
            else -> itemView.context.getString(R.string.trackss)
        }
    }
}