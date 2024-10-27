package com.practicum.playlistmaker.media.data.repository

import com.practicum.playlistmaker.media.data.converter.TrackDBConverter
import com.practicum.playlistmaker.media.data.db.AppDatabase
import com.practicum.playlistmaker.media.data.db.entity.TrackEntity
import com.practicum.playlistmaker.media.domain.db.FavoritesRepository
import com.practicum.playlistmaker.search.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoritesRepositoryImpl(
    private val database: AppDatabase,
    private val trackDBConverter: TrackDBConverter,
) : FavoritesRepository {
    override fun getTrackById(trackId: String): Flow<Track> = flow {
        val track = database.trackDao().getTrackById(trackId)
        emit(trackDBConverter.map(track))
    }

    override fun getFavoriteTracks(): Flow<List<Track>> = flow {
        val tracks = database.trackDao().getAllTracks()
        emit(convertFromTrackEntity(tracks))
    }

    override fun addToFavorite(track: Track) {
        database.trackDao().insertTrack(trackDBConverter.map(track))
    }

    override fun removeFromFavorite(trackId: String) {
        database.trackDao().deleteTrack(trackId)
    }

    private fun convertFromTrackEntity(tracks: List<TrackEntity>): List<Track> {
        return tracks.map { track -> trackDBConverter.map(track) }
    }

}