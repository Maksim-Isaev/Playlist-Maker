package com.practicum.playlistmaker.presentation.ui.player

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.presentation.ui.search.SearchActivity
import java.text.SimpleDateFormat
import java.util.Locale

class AudioPlayer : AppCompatActivity() {

    companion object {

        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val TIMER_UPDATE_DELAY = 250L
    }


    private var playerState = STATE_DEFAULT
    private var mediaPlayer = MediaPlayer()
    private lateinit var playButton: ImageButton
    private lateinit var playingTime: TextView
    private val handler = Handler(Looper.getMainLooper())
    private val timerRunnable by lazy {
        object : Runnable {
            override fun run() {
                if (playerState == STATE_PLAYING) {
                    playingTime.text = dateFormat.format(mediaPlayer.currentPosition)
                    handler.postDelayed(this, TIMER_UPDATE_DELAY)
                }
            }
        }
    }
    private val dateFormat by lazy { SimpleDateFormat("mm:ss", Locale.getDefault()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_player)

        val backButton = findViewById<Toolbar>(R.id.toolbar)
        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val track = intent.getSerializableExtra(SearchActivity.INTENT_TRACK_KEY) as Track

        val albumImage = findViewById<ImageView>(R.id.iv_album)
        val albumMainText = findViewById<TextView>(R.id.tv_main_album)
        val artistText = findViewById<TextView>(R.id.tv_artist)
        val trackTimeText = findViewById<TextView>(R.id.tv_track_time_value)
        val albumSecondText = findViewById<TextView>(R.id.tv_album_value)
        val yearText = findViewById<TextView>(R.id.tv_year_value)
        val genreText = findViewById<TextView>(R.id.tv_genre_value)
        val countryText = findViewById<TextView>(R.id.tv_country_value)
        playButton = findViewById(R.id.iv_play_or_stop)
        playingTime = findViewById(R.id.tv_track_timer)

        if (track != null) {
            preparePlayer(track.previewUrl)
        Glide.with(this)
            .load(track.artworkUrl512)
            .placeholder(R.drawable.ic_placeholder)
            .centerCrop()
            .transform(RoundedCorners(this.resources.getDimensionPixelSize(R.dimen.dp_10)))
            .into(albumImage)

        albumMainText.text = track.trackName
        artistText.text = track.artistName
        trackTimeText.text =
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
        albumSecondText.text = track.trackName
        yearText.text = track.releaseDate.substring(0, 4)
        genreText.text = track.primaryGenreName
        countryText.text = track.country
            playButton.setOnClickListener {
                playbackControl()
            }
        } else {
            albumImage.setImageResource(R.drawable.nothing)
        }
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        handler.removeCallbacks(timerRunnable)
    }

    private fun preparePlayer(url: String) {
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playButton.isEnabled = true
            playerState = STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            handler.removeCallbacks(timerRunnable)
            playerState = STATE_PREPARED
            playButton.setBackgroundResource(R.drawable.ic_play)
            playingTime.text = dateFormat.format(0)
        }
    }

    private fun playbackControl() {
        when (playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }

            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }
        }
    }

    private fun startPlayer() {
        mediaPlayer.start()
        playButton.setBackgroundResource(R.drawable.ic_pause)
        playerState = STATE_PLAYING
        handler.post(timerRunnable)
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        playButton.setBackgroundResource(R.drawable.ic_play)
        playerState = STATE_PAUSED
        handler.removeCallbacks(timerRunnable)
    }
}


