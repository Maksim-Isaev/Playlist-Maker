package com.practicum.playlistmaker.di

import android.content.Context
import com.google.gson.Gson
import com.practicum.playlistmaker.search.data.NetworkClient
import com.practicum.playlistmaker.search.data.network.ItunesApi
import com.practicum.playlistmaker.search.data.network.RetrofitNetworkClient
import com.practicum.playlistmaker.search.data.repository.SearchHistoryRepositoryImpl
import com.practicum.playlistmaker.search.data.repository.TrackRepositoryImpl
import com.practicum.playlistmaker.search.domain.api.SearchHistoryInteractor
import com.practicum.playlistmaker.search.domain.api.SearchHistoryRepository
import com.practicum.playlistmaker.search.domain.api.TrackInteractor
import com.practicum.playlistmaker.search.domain.api.TrackRepository
import com.practicum.playlistmaker.search.domain.impl.SearchHistoryInteractorImpl
import com.practicum.playlistmaker.search.domain.impl.TrackInteractorImpl
import com.practicum.playlistmaker.search.ui.SearchViewModel
import com.practicum.playlistmaker.utils.PLAYLISTMAKER_PREFERENCES
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val searchModule = module {
    single {
        androidContext().getSharedPreferences(PLAYLISTMAKER_PREFERENCES, Context.MODE_PRIVATE)
    }
    single { Gson() }
    single<ItunesApi> {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ItunesApi::class.java)
    }
    single<NetworkClient> { RetrofitNetworkClient() }


    single<TrackRepository> { TrackRepositoryImpl(get()) }
    single<SearchHistoryRepository> {
        SearchHistoryRepositoryImpl(
            get(),
            get()
        )
    }

    factory<SearchHistoryInteractor> {
        SearchHistoryInteractorImpl(
            get(),
            get()
        )
    }
    factory<TrackInteractor> { TrackInteractorImpl(get()) }

    viewModel {
        SearchViewModel(get(),
            get(),
            get()
        )
    }
}
