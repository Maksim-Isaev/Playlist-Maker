package com.practicum.playlistmaker.search.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import com.practicum.playlistmaker.search.data.dto.ItunesResponse

interface ItunesApi {
    @GET("/search?entity=song")
    suspend fun search(@Query("term") text: String): ItunesResponse
}