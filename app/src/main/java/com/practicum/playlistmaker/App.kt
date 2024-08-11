package com.practicum.playlistmaker

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.practicum.playlistmaker.creator.Creator.initApplication
import com.practicum.playlistmaker.creator.Creator.provideSharedPreferences

// Константа для имени файла с настройками
const val PLAYLISTMAKER_PREFERENCES = "_preferences"
// Константа для ключа настройки ночного режима
const val NIGHTMODE_ENABLED = "night_Mode_enabled"


class App : Application() {

    // Переменная для хранения состояния ночного режима
    var nightMode = false
        private set(value) {
            field = value
        }

    // Переменная для работы с SharedPreferences (хранилище настроек)
    private lateinit var sharedPrefs: SharedPreferences

    // Метод, который вызывается при создании приложения
    override fun onCreate() {
        super.onCreate()
        initApplication(this)
        sharedPrefs = provideSharedPreferences()

        switchTheme(sharedPrefs.getBoolean(NIGHTMODE_ENABLED, nightMode))
    }

    // Метод для переключения темы приложения
    fun switchTheme(darkThemeEnabled: Boolean) {
        nightMode = darkThemeEnabled
        sharedPrefs.edit().putBoolean(NIGHTMODE_ENABLED, nightMode).apply()
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}
