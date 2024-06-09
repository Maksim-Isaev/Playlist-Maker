package com.practicum.playlistmaker

data class Track(
    val trackId: String,
    val trackName: String,      //Track Name
    val artistName: String,     //Artist Name
    val trackTimeMillis: Long,  //Track duration
    val artworkUrl100: String   //Link to the cover image
)