package com.practicum.playlistmaker.search.domain.api

import com.practicum.playlistmaker.search.domain.model.Resource
import com.practicum.playlistmaker.search.domain.model.Track

interface TrackInteractor {
    fun search(expression: String, consumer: TrackConsumer)

    interface TrackConsumer {
        fun consume(foundTracks: Resource<List<Track>>)
        fun onFailure(t: Throwable)
        fun consume(foundTracks: Resource<List<Track>>, request: String)
    }
}