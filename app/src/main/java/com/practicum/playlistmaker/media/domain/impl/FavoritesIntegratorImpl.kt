package com.practicum.playlistmaker.media.domain.impl

import com.practicum.playlistmaker.media.domain.api.FavoritesInteractor
import com.practicum.playlistmaker.media.domain.api.FavoritesRepository
import com.practicum.playlistmaker.search.domain.model.Track
import kotlinx.coroutines.flow.Flow

class FavoritesInteractorImpl(
    private val repository: FavoritesRepository,
) : FavoritesInteractor {
    override fun isFavorite(trackId: String): Boolean {
        return repository.getTrackById(trackId).let { true }
    }

    override fun getTrackById(trackId: String): Flow<Track?> = repository.getTrackById(trackId)

    override fun getFavoriteTracks(): Flow<List<Track>> = repository.getFavoriteTracks()

    override fun addToFavorite(track: Track) {
        repository.addToFavorite(track)
        track.isFavorite = true
    }

    override fun removeFromFavorite(track: Track) {
        repository.removeFromFavorite(track.trackId)
        track.isFavorite = false
    }
}