package com.practicum.playlistmaker.utils

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

import com.practicum.playlistmaker.di.playerModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import com.practicum.playlistmaker.di.searchModule
import com.practicum.playlistmaker.di.settingsModule
import com.practicum.playlistmaker.settings.domain.MainThemeInteractor



class App : Application() {
    var darkTheme = false
        private set

    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                searchModule,
                playerModule,
                settingsModule,
            )
        }
        val mainThemeInt: MainThemeInteractor by inject()
        switchTheme(mainThemeInt.isNightTheme())
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }

}



// коммент для ревьюера : я коссякнул с пулл реквестами, и случайно закрыл реквест 16 спринта с 17....