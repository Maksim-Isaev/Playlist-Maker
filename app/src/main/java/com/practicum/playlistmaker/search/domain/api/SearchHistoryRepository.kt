package com.practicum.playlistmaker.search.domain.api

import com.practicum.playlistmaker.search.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {
    fun updateTracks(): Flow<List<Track>>
    fun addTrack(newTrack: Track)
    fun clearHistory()
}
