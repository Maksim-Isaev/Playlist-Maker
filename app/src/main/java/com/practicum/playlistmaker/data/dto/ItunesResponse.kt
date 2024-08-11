package com.practicum.playlistmaker.data.dto

//Класс, представляющий ответ от iTunes API.
class ItunesResponse(
    val results: List<TrackDto>,
) : Response()
