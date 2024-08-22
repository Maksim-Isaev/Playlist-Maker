package com.practicum.playlistmaker.player.ui

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.ActivityPlayerBinding
import com.practicum.playlistmaker.player.domain.model.PlayingState
import com.practicum.playlistmaker.search.domain.model.Track
import com.practicum.playlistmaker.search.ui.SearchActivity
import com.practicum.playlistmaker.utils.dpToPx
import com.practicum.playlistmaker.utils.getReleaseYear
import java.util.Locale

class TrackPlayerActivity : AppCompatActivity() {

    private val binding: ActivityPlayerBinding by lazy {
        ActivityPlayerBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: TrackPlayerViewModel

    private val dateFormat by lazy { SimpleDateFormat("mm:ss", Locale.getDefault()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.statusBarColor = resources.getColor(R.color.status_bar, theme)
        window.navigationBarColor = resources.getColor(R.color.navigation_bar, theme)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        val track = intent.getParcelableExtra(SearchActivity.TRACK_DATA) as? Track

        if (track != null) {
            viewModel = ViewModelProvider(
                this,
                TrackPlayerViewModel.getViewModelFactory(track.previewUrl)
            )[TrackPlayerViewModel::class.java]
            render(track)

            viewModel.observePlayingState().observe(this) { state ->
                binding.playButton.isEnabled = state != PlayingState.Default
                setButtonImage(state)
                viewModel.stateControl()
            }

            viewModel.observePositionState().observe(this) {
                binding.playingTime.text = dateFormat.format(it)
            }
        } else {
            binding.albumCover.setImageResource(R.drawable.nothing)
        }
    }

    private fun render(track: Track) {
        binding.playButton.isEnabled = false
        Glide.with(this)
            .load(track.getCoverArtwork())
            .placeholder(R.drawable.ic_placeholder)
            .centerCrop()
            .transform(RoundedCorners(dpToPx(8f, this)))
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
    }

    private fun setButtonImage(state: PlayingState) {
        binding.playButton.setImageDrawable(null)
        val drawable = when (state) {
            is PlayingState.Default,
            PlayingState.Prepared,
            PlayingState.Paused,
            PlayingState.Complete,
            -> R.drawable.ic_play

            is PlayingState.Playing -> R.drawable.ic_pause
        }
        binding.playButton.setImageResource(drawable)
    }


}