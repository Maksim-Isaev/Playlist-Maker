package com.practicum.playlistmaker.domain.api

import com.practicum.playlistmaker.domain.model.Track


interface TrackInteractor {
    fun search(expression: String, consumer: TrackConsumer)

    interface TrackConsumer {
        fun consume(foundTracks: List<Track>)
        fun onFailure(t: Throwable)
    }
}