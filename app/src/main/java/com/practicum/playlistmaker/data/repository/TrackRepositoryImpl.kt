package com.practicum.playlistmaker.data.repository

import com.practicum.playlistmaker.data.NetworkClient
import com.practicum.playlistmaker.data.dto.ItunesResponse
import com.practicum.playlistmaker.data.dto.TrackRequest
import com.practicum.playlistmaker.domain.api.TrackRepository
import com.practicum.playlistmaker.domain.model.Track

//Реализация репозитория для поиска треков.

class TrackRepositoryImpl(private val networkClient: NetworkClient) : TrackRepository {
    override fun searchTracks(expression: String): List<Track> {

        // Выполняем сетевой запрос с использованием TrackRequest

        val response = networkClient.doRequest(TrackRequest(expression))
        if (response.resultCode == 200) {

            // Преобразуем ItunesResponse в список объектов Track

            return (response as ItunesResponse).results.map {
                Track(
                    it.trackId,
                    it.trackName,
                    it.artistName,
                    it.trackTimeMillis,
                    it.artworkUrl100,
                    it.collectionName,
                    it.releaseDate,
                    it.primaryGenreName,
                    it.country,
                    it.previewUrl
                )
            }
        } else {
            return emptyList()
        }
    }
}