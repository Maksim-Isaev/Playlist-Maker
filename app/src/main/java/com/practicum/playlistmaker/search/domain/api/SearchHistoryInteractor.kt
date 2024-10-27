package com.practicum.playlistmaker.search.domain.api

import com.practicum.playlistmaker.search.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface SearchHistoryInteractor {
    fun getHistory(): Flow<List<Track>>

    fun clearHistory()

    fun addToHistory(track: Track)
}