package com.practicum.playlistmaker.search.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.practicum.playlistmaker.search.data.NetworkClient
import com.practicum.playlistmaker.search.data.dto.Response
import com.practicum.playlistmaker.search.data.dto.TrackRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class RetrofitNetworkClient : NetworkClient {

    private val baseUrl = "https://itunes.apple.com/"
    private val client: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val api = client.create(ItunesApi::class.java)


    override suspend fun doRequest(dto: Any): Response {
        return withContext(Dispatchers.IO) {
            try {
                if (dto is TrackRequest) {
                    val response = api.search(dto.expression)
                    return@withContext response.apply { resultCode = 200 }
                } else {
                    return@withContext Response(400)
                }
            } catch (error: Throwable) {
                return@withContext Response(500)
            }
        }
    }
}