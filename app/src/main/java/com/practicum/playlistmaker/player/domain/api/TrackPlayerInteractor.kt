package com.practicum.playlistmaker.player.domain.api

interface TrackPlayerInteractor {


    fun start()
    fun pause()
    fun release()
    fun getCurrentPosition(): Int
    fun prepare(onCompletionListener: () -> Unit)
    fun isPlaying(): Boolean
}