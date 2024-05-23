package com.practicum.playlistmaker.api

// Перечисления (enum) для представления различных состояний ответа API
enum class ResultResponse {
    SUCCESS,     // Успешный ответ от API
    EMPTY,       // Отсутствие результатов (пустой ответ)
    ERROR        // Ошибка при выполнении запроса
}