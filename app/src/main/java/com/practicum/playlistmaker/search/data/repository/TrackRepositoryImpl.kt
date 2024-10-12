package com.practicum.playlistmaker.search.data.repository

import com.practicum.playlistmaker.search.data.NetworkClient
import com.practicum.playlistmaker.search.data.dto.ItunesResponse
import com.practicum.playlistmaker.search.data.dto.TrackRequest
import com.practicum.playlistmaker.search.domain.api.TrackRepository
import com.practicum.playlistmaker.search.domain.model.Resource
import com.practicum.playlistmaker.search.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TrackRepositoryImpl(private val networkClient: NetworkClient) : TrackRepository {
    override fun searchTracks(expression: String): Flow<Resource<List<Track>>> = flow {
        val response = networkClient.doRequest(TrackRequest(expression))
        when (response.resultCode) {
            200 -> {
                emit(Resource.Success((response as ItunesResponse).results.map {
                    Track(
                        trackId = it.trackId,
                        trackName = it.trackName,
                        artistName = it.artistName,
                        trackTimeMillis = it.trackTimeMillis,
                        artworkUrl100 = it.artworkUrl100,
                        collectionName = it.collectionName,
                        primaryGenreName = it.primaryGenreName,
                        releaseDate = it.releaseDate,
                        country = it.country,
                        previewUrl = it.previewUrl
                    )
                }))
            }

            -1 -> {
               emit(Resource.Error("Проверьте подключение к интернету"))
            }

            else -> {
                emit(Resource.Error("Ошибка сервера"))
            }
        }
    }
}