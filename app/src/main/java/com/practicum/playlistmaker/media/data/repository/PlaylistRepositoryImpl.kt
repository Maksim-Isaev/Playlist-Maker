package com.practicum.playlistmaker.media.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.practicum.playlistmaker.media.data.converter.PlaylistDBConverter
import com.practicum.playlistmaker.media.data.db.AppDatabase
import com.practicum.playlistmaker.media.data.db.entity.PlaylistEntity
import com.practicum.playlistmaker.media.domain.api.PlaylistRepository
import com.practicum.playlistmaker.media.ui.model.Playlist

class PlaylistRepositoryImpl(
    private val database: AppDatabase,
    private val playlistDBConverter: PlaylistDBConverter,
) : PlaylistRepository {
    override fun getPlaylists(): Flow<List<Playlist>> = flow {
        val playlists = database.playlistDao().getAllPlaylists()
        emit(convertFromPlaylistEntity(playlists))
    }

    override fun createPlaylist(
        playlistName: String,
        playlistDescription: String,
        playlistImage: String?,
    ): Long {
        return database.playlistDao().insertPlaylist(
            PlaylistEntity(
                name = playlistName,
                description = playlistDescription,
                imagePath = playlistImage,
                tracks = "",
                tracksCount = 0
            )
        )
    }

    override fun addToPlaylist(trackId: String, playlistId: Int): Boolean {
        val playlist = database.playlistDao().getPlaylistById(playlistId)
        val jsonTracks = playlist.tracks
        var tracks = ArrayList<String>()
        if (jsonTracks.isNotEmpty())
            tracks = playlistDBConverter.createTracksFromJson(jsonTracks)


        val currentTrack = tracks.filter { track -> track == trackId }
        if (currentTrack.isEmpty()) {
            tracks.add(trackId)
            database.playlistDao().updatePlaylist(
                playlist.copy(
                    tracks = playlistDBConverter.createJsonFromTracks(tracks),
                    tracksCount = tracks.size
                )
            )
            return true
        }
        return false
    }

    override fun removeFromPlaylist(trackId: String, playlistId: Int) {
        val playlist = database.playlistDao().getPlaylistById(playlistId)
        val jsonTracks = playlist.tracks
        if (jsonTracks.isNotEmpty()) {
            val tracks = playlistDBConverter.createTracksFromJson(jsonTracks)
            val currentTrack = tracks.filter { track -> track == trackId }
            if (currentTrack.isNotEmpty()) {
                tracks.remove(trackId)
                database.playlistDao().updatePlaylist(
                    playlist.copy(
                        tracks = playlistDBConverter.createJsonFromTracks(tracks),
                        tracksCount = tracks.size
                    )
                )
            }
        }
    }

    fun convertFromPlaylistEntity(playlists: List<PlaylistEntity>): List<Playlist> {
        return playlists.map { playlist -> playlistDBConverter.map(playlist) }
    }
}