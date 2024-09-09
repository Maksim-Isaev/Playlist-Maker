package com.practicum.playlistmaker.player.data

import android.media.MediaPlayer
import com.practicum.playlistmaker.player.domain.api.TrackPlayerInteractor
import com.practicum.playlistmaker.player.domain.model.PlayingState

class TrackPlayerImpl(
    private val mediaPlayer: MediaPlayer,
    private val trackUrl: String,
) : TrackPlayerInteractor {

    override var state: PlayingState = PlayingState.Default

    override fun prepare() {
        try {
            mediaPlayer.reset()
            mediaPlayer.setDataSource(trackUrl)
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener {
                state = PlayingState.Prepared
            }
            mediaPlayer.setOnCompletionListener {
                state = PlayingState.Complete
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            state = PlayingState.Default
        }
    }

    override fun start() {
        if (state == PlayingState.Prepared || state == PlayingState.Paused) {
            mediaPlayer.start()
            state = PlayingState.Playing
        }
    }

    override fun pause() {
        if (state == PlayingState.Playing) {
            mediaPlayer.pause()
            state = PlayingState.Paused
        }
    }

    override fun release() {
        mediaPlayer.release()
        state = PlayingState.Default
    }

    override fun getCurrentPosition(): Int {
        return mediaPlayer.currentPosition
    }
}
