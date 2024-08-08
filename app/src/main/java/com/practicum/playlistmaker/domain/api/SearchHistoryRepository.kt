package com.practicum.playlistmaker.domain.api

import com.practicum.playlistmaker.domain.model.Track


interface SearchHistoryRepository {
    fun updateTracks(): List<Track>
    fun addTrack(newTrack: Track)
    fun clearHistory()
}
