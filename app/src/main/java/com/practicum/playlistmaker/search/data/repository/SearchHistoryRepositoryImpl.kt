package com.practicum.playlistmaker.search.data.repository


import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.practicum.playlistmaker.media.data.db.AppDatabase
import com.practicum.playlistmaker.search.domain.api.SearchHistoryRepository
import com.practicum.playlistmaker.search.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchHistoryRepositoryImpl(
    private val preferences: SharedPreferences,
    private val appDB: AppDatabase,
) :
    SearchHistoryRepository {
    companion object {
        private const val HISTORY_KEY = "track_history"
        private const val MAX_HISTORY_SIZE = 10
    }

    override fun updateTracks(): Flow<List<Track>> = flow {
        var tracks = ArrayList<Track>()
        val jsonTracks = preferences.getString(HISTORY_KEY, null)
        if (jsonTracks != null) {
            tracks = createTracksFromJson(jsonTracks)
            tracks.forEach {
                it.isFavorite = appDB.trackDao().isFavorite(it.trackId)
            }
        }
        emit(tracks)
    }

    override fun addTrack(newTrack: Track) {
        var trackList = ArrayList<Track>()
        val jsonTracks = preferences.getString(HISTORY_KEY, null)
        if (jsonTracks != null) {
            trackList = createTracksFromJson(jsonTracks)
            trackList.removeIf { it.trackId == newTrack.trackId }
            if (trackList.size >= MAX_HISTORY_SIZE)
                trackList.removeAt(MAX_HISTORY_SIZE - 1)
        }
        trackList.add(0, newTrack)
        preferences.edit {
            putString(HISTORY_KEY, createJsonFromTracks(trackList))
        }
    }
    override fun clearHistory() {
        preferences.edit {
            remove(HISTORY_KEY)
        }
    }
    private fun createJsonFromTracks(tracks: ArrayList<Track>): String {
        return Gson().toJson(tracks)
    }
    private fun createTracksFromJson(json: String): ArrayList<Track> {
        val trackListType = object : TypeToken<ArrayList<Track>>() {}.type
        return Gson().fromJson(json, trackListType)
    }
}
