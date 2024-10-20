package com.practicum.playlistmaker.di

import androidx.room.Room
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.practicum.playlistmaker.media.ui.media.FavoritesViewModel
import com.practicum.playlistmaker.media.ui.media.PlaylistViewModel
import com.practicum.playlistmaker.search.data.db.AppDatabase
import com.practicum.playlistmaker.search.data.repository.FavoritesRepo
import com.practicum.playlistmaker.search.data.repository.FavoritesRepoImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.get

val mediaModule = module {
    viewModel {
        FavoritesViewModel(get())
    }

    viewModel {
        PlaylistViewModel()
    }
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "play-list-maker-db")
            .build()
    }

    single<FavoritesRepo> { FavoritesRepoImpl(get()) }

}