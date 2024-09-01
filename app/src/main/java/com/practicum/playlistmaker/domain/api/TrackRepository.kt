package com.practicum.playlistmaker.domain.api

import com.practicum.playlistmaker.domain.model.Track

interface TrackRepository {
    fun searchTracks(expression: String): List<Track>
}