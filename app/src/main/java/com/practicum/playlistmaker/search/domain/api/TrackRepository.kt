package com.practicum.playlistmaker.search.domain.api

import com.practicum.playlistmaker.search.domain.model.Resource
import com.practicum.playlistmaker.search.domain.model.Track

interface TrackRepository {
    fun searchTracks(expression: String): Resource<List<Track>>
}