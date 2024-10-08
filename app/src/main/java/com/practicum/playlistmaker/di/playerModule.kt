package com.practicum.playlistmaker.di

import android.media.MediaPlayer
import com.practicum.playlistmaker.player.data.TrackPlayerImpl
import com.practicum.playlistmaker.player.domain.api.TrackPlayerInteractor
import com.practicum.playlistmaker.player.ui.TrackPlayerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val playerModule = module {

    single { MediaPlayer() }

    factory<TrackPlayerInteractor> { (url: String) ->
        TrackPlayerImpl(get(), url)
    }

    viewModel { (url: String) ->
        TrackPlayerViewModel(
            trackPlayerInteractor = get { parametersOf(url) }
        )
    }
}