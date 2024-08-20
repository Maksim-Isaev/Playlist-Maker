package com.practicum.playlistmaker.player.domain.api

import com.practicum.playlistmaker.player.domain.model.PlayingState

interface TrackPlayerInteractor {

    var state: PlayingState
    fun prepare()
    fun start()
    fun pause()
    fun release()
    fun getCurrentPosition(): Int
}