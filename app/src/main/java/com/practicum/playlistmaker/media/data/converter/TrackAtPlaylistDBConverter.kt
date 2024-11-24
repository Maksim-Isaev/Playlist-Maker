package com.practicum.playlistmaker.media.data.converter

import com.practicum.playlistmaker.media.data.db.entity.TrackAtPlaylistEntity
import com.practicum.playlistmaker.search.domain.model.Track
import com.practicum.playlistmaker.utils.getPreviewUrl

class TrackAtPlaylistDBConverter {
    fun map(track: Track): TrackAtPlaylistEntity {
        return TrackAtPlaylistEntity(
            trackId = track.trackId,
            trackName = track.trackName,
            artistName = track.artistName,
            trackTimeMillis = track.trackTimeMillis,
            artworkUrl100 = track.artworkUrl100,
            previewUrl = getPreviewUrl(track.previewUrl),
            collectionName = track.collectionName,
            primaryGenreName = track.primaryGenreName ?: "unknown",
            releaseDate = track.releaseDate.orEmpty(),
            country = track.country,
            addedAt = System.currentTimeMillis().toString(),
            isFavorite = track.isFavorite

        )
    }

    fun map(trackEntity: TrackAtPlaylistEntity): Track {
        return Track(
            trackId = trackEntity.trackId,
            trackName = trackEntity.trackName,
            artistName = trackEntity.artistName,
            trackTimeMillis = trackEntity.trackTimeMillis,
            artworkUrl100 = trackEntity.artworkUrl100,
            previewUrl = trackEntity.previewUrl,
            collectionName = trackEntity.collectionName,
            primaryGenreName = trackEntity.primaryGenreName,
            releaseDate = trackEntity.releaseDate,
            country = trackEntity.country,
            isFavorite = trackEntity.isFavorite
        )
    }
}