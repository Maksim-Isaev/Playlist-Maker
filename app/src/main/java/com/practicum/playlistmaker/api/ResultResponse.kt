package com.practicum.playlistmaker.api

// Перечисления (enum) для представления различных состояний ответа API
enum class ResultResponse {
    SUCCESS,
    EMPTY,
    ERROR,
    HISTORY
// Успешный ответ от API
// Отсутствие результатов (пустой ответ)
// Ошибка при выполнении запроса
// История запросов
}