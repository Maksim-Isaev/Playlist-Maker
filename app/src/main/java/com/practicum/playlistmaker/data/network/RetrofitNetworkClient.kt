package com.practicum.playlistmaker.data.network

import com.practicum.playlistmaker.data.NetworkClient
import com.practicum.playlistmaker.data.dto.Response
import com.practicum.playlistmaker.data.dto.TrackRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import java.io.IOException

class RetrofitNetworkClient : NetworkClient {

    //Класс для выполнения сетевых запросов с использованием Retrofit.

    private val baseUrl = "https://itunes.apple.com/"
    private val client: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val api = client.create(ItunesApi::class.java)

    // Выполняет сетевой запрос на основе переданного объекта DTO.

    override fun doRequest(dto: Any): Response {
        return try {
            if (dto is TrackRequest) {

                // Проверяем, является ли dto экземпляром TrackRequest

                val response = api.search(dto.expression).execute()
                val body = response.body() ?: Response()
                return body.apply { resultCode = response.code() }
            } else {

                // Если dto не является TrackRequest, возвращаем Response с кодом 400 (Bad Request)

                return Response().apply { resultCode = 400 }
            }
        } catch (error: IOException) {

            // Обрабатываем исключение ввода-вывода и возвращаем Response с кодом 500 (Internal Server Error)

            Response().apply { resultCode = 500 }
        }
    }
}