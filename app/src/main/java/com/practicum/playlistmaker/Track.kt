package com.practicum.playlistmaker

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

data class Track(
    val trackId: String,
    val trackName: String,      //Track Name
    val artistName: String,     //Artist Name
    val trackTimeMillis: Long,  //Track duration
    val artworkUrl100: String,   //Link to the cover image
    val collectionName: String,
    val releaseDate: String = "",
    val primaryGenreName: String = "",
    val country: String = ""
) : Serializable {
    val artworkUrl512: String
        get() = artworkUrl100.replaceAfterLast('/', "512x512bb.jpg")
    val trackTime: String
        get() = SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackTimeMillis)
}
