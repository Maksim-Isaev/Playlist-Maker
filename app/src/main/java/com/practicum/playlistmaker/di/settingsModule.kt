package com.practicum.playlistmaker.di

import android.content.Context
import android.content.SharedPreferences
import com.practicum.playlistmaker.settings.data.MainThemeInteractorImpl
import com.practicum.playlistmaker.settings.domain.MainThemeInteractor
import com.practicum.playlistmaker.settings.ui.SettingsViewModel
import com.practicum.playlistmaker.utils.PLAYLISTMAKER_PREFERENCES
import com.practicum.playlistmaker.sharing.domain.api.SharingRepository
import com.practicum.playlistmaker.sharing.data.SharingRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {
    single<MainThemeInteractor> { MainThemeInteractorImpl(get()) }


    single<SharedPreferences> {
        androidContext().getSharedPreferences(PLAYLISTMAKER_PREFERENCES, Context.MODE_PRIVATE)
    }

    single<SharingRepository> { SharingRepositoryImpl(get()) }

    viewModel {
        SettingsViewModel(
            sharingRepository = get(),
            themeInteractor = get()
        )
    }
}
