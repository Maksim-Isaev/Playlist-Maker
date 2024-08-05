package com.practicum.playlistmaker.data.dto

// Класс данных, представляющий музыкальный трек.
data class TrackDto(
    val trackId: String,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Long,
    val artworkUrl100: String,
    val collectionName: String,
    val primaryGenreName: String,
    val releaseDate: String,
    val country: String,
    val previewUrl: String,
)
// trackId Уникальный идентификатор трека.
// trackName Название трека.
// artistName Имя исполнителя трека.
// trackTimeMillis Продолжительность трека в миллисекундах.
// artworkUrl100 URL изображения обложки (размер 100x100 пикселей).
// collectionName Название альбома, к которому относится трек.
// primaryGenreName Основной жанр трека.
// releaseDate Дата выпуска трека.
// country Страна происхождения трека.
// previewUrl URL для предварительного прослушивания трека.