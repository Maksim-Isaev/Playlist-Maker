package com.practicum.playlistmaker.search.domain.impl

import com.practicum.playlistmaker.search.domain.api.TrackInteractor
import com.practicum.playlistmaker.search.domain.api.TrackRepository
import com.practicum.playlistmaker.search.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.Dispatchers

class TrackInteractorImpl(private val repository: TrackRepository) : TrackInteractor {

    override fun search(expression: String): Flow<List<Track>> {
        return repository.searchTracks(expression)
            .flowOn(Dispatchers.IO)
            .catch { emit(emptyList()) }  // Обработка ошибок
    }
}
