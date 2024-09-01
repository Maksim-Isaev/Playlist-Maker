package com.practicum.playlistmaker.search.domain.api

import com.practicum.playlistmaker.search.domain.model.Track

interface SearchHistoryRepository {
    fun updateTracks(): List<Track>
    fun addTrack(newTrack: Track)
    fun clearHistory()
}
