package com.practicum.playlistmaker.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.practicum.playlistmaker.media.ui.media.FavoritesViewModel
import com.practicum.playlistmaker.media.ui.media.PlaylistViewModel

val mediaModule = module {
    viewModel {
        FavoritesViewModel()
    }

    viewModel {
        PlaylistViewModel()
    }
}