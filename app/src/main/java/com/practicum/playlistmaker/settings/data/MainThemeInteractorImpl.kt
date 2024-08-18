package com.practicum.playlistmaker.settings.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.practicum.playlistmaker.settings.domain.MainThemeInteractor
import com.practicum.playlistmaker.utils.NIGHTMODE_ENABLED

class MainThemeInteractorImpl(val sharedPrefs: SharedPreferences) : MainThemeInteractor {
    private var darkTheme = false


    override fun isNightTheme(): Boolean {
        return sharedPrefs.getBoolean(NIGHTMODE_ENABLED, darkTheme)
    }

    override fun saveTheme(isNightTheme: Boolean) {
        darkTheme = isNightTheme
        sharedPrefs.edit { putBoolean(NIGHTMODE_ENABLED, darkTheme) }
    }
}