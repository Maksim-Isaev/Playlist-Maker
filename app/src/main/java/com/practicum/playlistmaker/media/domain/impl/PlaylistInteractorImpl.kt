package com.practicum.playlistmaker.media.domain.impl

import kotlinx.coroutines.flow.Flow
import com.practicum.playlistmaker.media.domain.api.PlaylistInteractor
import com.practicum.playlistmaker.media.domain.api.PlaylistRepository
import com.practicum.playlistmaker.media.ui.model.Playlist
import com.practicum.playlistmaker.search.domain.model.Track

class PlaylistInteractorImpl(
    private val repository: PlaylistRepository,
) : PlaylistInteractor {
    override fun getPlaylists(): Flow<List<Playlist>> = repository.getPlaylists()
    override fun getPlaylistById(playlistId: Int): Flow<Playlist?> =
        repository.getPlaylistById(playlistId)

    override fun getAllTracks(playlistId: Int): Flow<List<Track>?> =
        repository.getAllTracks(playlistId)

    override fun createPlaylist(
        playlistName: String,
        playlistDescription: String,
        playlistImage: String?,
    ): Long {
        return repository.createPlaylist(playlistName, playlistDescription, playlistImage)
    }

    override fun updatePlaylist(playlist: Playlist) {
        repository.updatePlaylist(playlist)
    }

    override fun addToPlaylist(track: Track, playlistId: Int): Boolean {
        return repository.addToPlaylist(track, playlistId)
    }

    override suspend fun removeFromPlaylist(trackId: String, playlistId: Int) {
        repository.removeFromPlaylist(trackId, playlistId)
    }

    override suspend fun deletePlaylist(playlistId: Int) {
        repository.deletePlaylist(playlistId)
    }
}