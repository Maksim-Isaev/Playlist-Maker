package com.practicum.playlistmaker.media.domain.db


import com.practicum.playlistmaker.search.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface FavoritesInteractor {
    fun isFavorite(trackId: String): Boolean
    fun getTrackById(trackId: String): Flow<Track?>
    fun getFavoriteTracks(): Flow<List<Track>>
    fun addToFavorite(track: Track)
    fun removeFromFavorite(track: Track)
}