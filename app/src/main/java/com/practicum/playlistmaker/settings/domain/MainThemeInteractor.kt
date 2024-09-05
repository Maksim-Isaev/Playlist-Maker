package com.practicum.playlistmaker.settings.domain

interface MainThemeInteractor {
    fun isNightTheme(): Boolean
    fun saveTheme(isNightTheme: Boolean)
}