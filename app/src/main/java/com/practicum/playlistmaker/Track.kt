package com.practicum.playlistmaker

data class Track(
    val trackName: String,      //Track Name
    val artistName: String,     //Artist Name
    val trackTime: String,      //Track duration
    val artworkUrl100: String   //Link to the cover image
)