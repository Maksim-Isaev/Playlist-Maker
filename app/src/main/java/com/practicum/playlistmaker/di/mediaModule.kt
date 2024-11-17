package com.practicum.playlistmaker.di

import androidx.room.Room
import com.practicum.playlistmaker.media.data.converter.PlaylistDBConverter
import com.practicum.playlistmaker.media.data.converter.TrackDBConverter
import com.practicum.playlistmaker.media.data.db.AppDatabase
import com.practicum.playlistmaker.media.data.repository.FavoritesRepositoryImpl
import com.practicum.playlistmaker.media.data.repository.PlaylistRepositoryImpl
import com.practicum.playlistmaker.media.domain.api.FavoritesInteractor
import com.practicum.playlistmaker.media.domain.api.FavoritesRepository
import com.practicum.playlistmaker.media.domain.api.PlaylistInteractor
import com.practicum.playlistmaker.media.domain.api.PlaylistRepository
import com.practicum.playlistmaker.media.domain.impl.FavoritesInteractorImpl
import com.practicum.playlistmaker.media.domain.impl.PlaylistInteractorImpl
import com.practicum.playlistmaker.media.ui.FavoritesViewModel
import com.practicum.playlistmaker.media.ui.NewPlaylistViewModel
import com.practicum.playlistmaker.media.ui.PlaylistViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module

val mediaModule = module {
    viewModel {
        FavoritesViewModel(get())
    }

    viewModel {
        PlaylistViewModel(get())
    }

    viewModel {
        NewPlaylistViewModel(get(), get())
    }
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "play-list-maker-db")
            .build()
    }
    single {
        TrackDBConverter()
    }
    single {
        PlaylistDBConverter()
    }
    single<FavoritesRepository> { FavoritesRepositoryImpl(get(), get()) }

    single<FavoritesInteractor> {
        FavoritesInteractorImpl(get())
    }
    single<PlaylistRepository> {
        PlaylistRepositoryImpl(get(), get())
    }

    single<PlaylistInteractor> {
        PlaylistInteractorImpl(get())
    }
}