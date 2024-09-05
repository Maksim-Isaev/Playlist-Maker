package com.practicum.playlistmaker.search.domain.api

import com.practicum.playlistmaker.search.domain.model.Track

interface SearchHistoryInteractor {
    fun getHistory(): List<Track>

    fun clearHistory()

    fun addToHistory(track: Track)
}