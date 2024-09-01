package com.practicum.playlistmaker.search.domain.impl

import com.practicum.playlistmaker.search.domain.api.SearchHistoryInteractor
import com.practicum.playlistmaker.search.domain.api.SearchHistoryRepository
import com.practicum.playlistmaker.search.domain.model.Track

class SearchHistoryInteractorImpl(
    private val searchHistoryRepository: SearchHistoryRepository,
) : SearchHistoryInteractor {
    override fun getHistory(): List<Track> {
        return searchHistoryRepository.updateTracks()
    }

    override fun clearHistory() {
        searchHistoryRepository.clearHistory()
    }

    override fun addToHistory(track: Track) {
        searchHistoryRepository.addTrack(track)
    }
}