package com.practicum.playlistmaker.search.domain.impl

import com.practicum.playlistmaker.search.domain.api.SearchHistoryInteractor
import com.practicum.playlistmaker.search.domain.api.SearchHistoryRepository
import com.practicum.playlistmaker.search.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchHistoryInteractorImpl(
    private val searchHistoryRepository: SearchHistoryRepository,
) : SearchHistoryInteractor {
    override fun getHistory(): Flow<List<Track>> = flow {
        searchHistoryRepository.updateTracks().collect { tracks ->
            emit(tracks)
        }
    }

    override fun clearHistory() {
        searchHistoryRepository.clearHistory()
    }

    override fun addToHistory(track: Track) {
        searchHistoryRepository.addTrack(track)
    }
}