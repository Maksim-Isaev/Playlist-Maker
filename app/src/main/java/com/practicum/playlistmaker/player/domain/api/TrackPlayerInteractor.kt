package com.practicum.playlistmaker.player.domain.api

import com.practicum.playlistmaker.player.domain.model.PlayingState

interface TrackPlayerInteractor {


    fun start()
    fun pause()
    fun release()
    fun getCurrentPosition(): Int
    fun prepare(onCompletionListener: () -> Unit)
    fun isPlaying(): Boolean
}