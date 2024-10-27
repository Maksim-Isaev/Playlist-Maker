package com.practicum.playlistmaker.player.ui

import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.ActivityPlayerBinding
import com.practicum.playlistmaker.player.ui.model.PlayerState
import com.practicum.playlistmaker.search.domain.model.Track
import com.practicum.playlistmaker.utils.convertDpToPx
import com.practicum.playlistmaker.utils.getReleaseYear
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.Locale

class TrackPlayerActivity : AppCompatActivity() {
    private val binding: ActivityPlayerBinding by lazy {
        ActivityPlayerBinding.inflate(layoutInflater)
    }

    private val dateFormat by lazy { SimpleDateFormat(TIME_PATTERN, Locale.getDefault()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.statusBarColor = resources.getColor(R.color.status_bar, theme)
        window.navigationBarColor = resources.getColor(R.color.navigation_bar, theme)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        val track = intent.getParcelableExtra(TRACK_KEY) as? Track
        if (track != null) {
            val viewModel: TrackPlayerViewModel by viewModel {
                parametersOf(track.previewUrl)
            }
            render(track, viewModel)

            binding.favoriteBtn.setOnClickListener {
                viewModel.onFavoriteClick(track)
            }

            viewModel.observeFavoriteState().observe(this) { state ->
                updateFavoriteState(state)
            }

            viewModel.observePlayingState().observe(this) { state ->
                updateState(state)
            }

        } else {
            binding.albumCover.setImageResource(R.drawable.nothing)
        }
    }

    private fun render(track: Track, viewModel: TrackPlayerViewModel) {
        binding.playButton.isEnabled = false
        Glide.with(this).load(track.getCoverArtwork()).placeholder(R.drawable.ic_placeholder)
            .centerCrop().transform(RoundedCorners(convertDpToPx(8f, this)))
            .into(binding.albumCover)
        binding.title.text = track.trackName
        binding.artistName.text = track.artistName
        binding.playingTime.text = getString(R.string.time_zero)
        binding.duration.text = dateFormat.format(track.trackTimeMillis)
        binding.album.text = track.collectionName
        binding.year.text = getReleaseYear(track.releaseDate)
        binding.genre.text = track.primaryGenreName
        binding.country.text = track.country
        binding.playButton.setOnClickListener {
            viewModel.playingControl()
        }
        updateFavoriteState(track.isFavorite)
    }

    private fun updateFavoriteState(isFavorite: Boolean) {
        if (isFavorite) {
            binding.favoriteBtn.setImageResource(R.drawable.ic_liked)
        } else {
            binding.favoriteBtn.setImageResource(R.drawable.ic_like)
        }
    }

    private fun updateState(state: PlayerState) {
        binding.playButton.isEnabled = state.isPlayButtonEnabled
        binding.playingTime.text = state.progress
        binding.playButton.setImageDrawable(AppCompatResources.getDrawable(this, state.buttonIcon))
    }

    companion object {
        private const val TIME_PATTERN = "mm:ss"
        const val TRACK_KEY = "TRACK_KEY"
        fun newInstance(context: Context, track: Track): Intent {
            return Intent(context, TrackPlayerActivity::class.java).apply {
                putExtra(TRACK_KEY, track)
            }
        }
    }
}