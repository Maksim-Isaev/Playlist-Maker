package com.practicum.playlistmaker.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// Определение интерфейса для взаимодействия с API iTunes
// Объявление метода для поиска песен. Метод использует HTTP GET запрос с параметром "term".
// Параметр "term" будет содержать текст для поиска, и запрос будет добавлять к URL параметр "?entity=song".
// Возвращается объект Call, обёрнутый в ItunesResponse.
interface api {
    @GET("/search?entity=song")
    fun search(@Query("term") text: String): Call<ItunesResponse>
}