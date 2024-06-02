package com.practicum.playlistmaker.api

import com.practicum.playlistmaker.Track

// Класс для представления ответа от API iTunes
// Содержит список треков (results), полученных в результате запроса

class ItunesResponse(val results: ArrayList<Track>) {
}