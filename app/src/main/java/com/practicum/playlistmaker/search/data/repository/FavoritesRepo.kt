package com.practicum.playlistmaker.search.data.repository

import com.practicum.playlistmaker.search.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface FavoritesRepo {
    suspend fun insertFavorite(track: Track)
    suspend fun deleteFavorite(track: Track)
    fun getFavorites(): Flow<List<Track>>
    fun getFavoritesIds(): Flow<List<String>>
}