package com.practicum.playlistmaker.media.domain.api

import kotlinx.coroutines.flow.Flow
import com.practicum.playlistmaker.media.ui.model.Playlist
import com.practicum.playlistmaker.search.domain.model.Track

interface PlaylistInteractor {
    fun getPlaylists(): Flow<List<Playlist>>
    fun getPlaylistById(playlistId: Int): Flow<Playlist?>
    fun getAllTracks(playlistId: Int): Flow<List<Track>?>
    fun createPlaylist(
        playlistName: String,
        playlistDescription: String,
        playlistImage: String?,
    ): Long
    fun updatePlaylist(playlist: Playlist)
    fun addToPlaylist(track: Track, playlistId: Int): Boolean
    suspend fun removeFromPlaylist(trackId: String, playlistId: Int)
    suspend fun deletePlaylist(playlistId: Int)
}