package com.practicum.playlistmaker.creator

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.practicum.playlistmaker.data.network.RetrofitNetworkClient
import com.practicum.playlistmaker.data.repository.SearchHistoryRepositoryImpl
import com.practicum.playlistmaker.data.repository.TrackRepositoryImpl
import com.practicum.playlistmaker.domain.api.TrackInteractor
import com.practicum.playlistmaker.domain.api.TrackRepository
import com.practicum.playlistmaker.domain.impl.TrackInteractorImpl

object Creator {
    private const val PLAYLISTMAKER_PREFERENCES = "_preferences"

    private lateinit var application: Application

    fun initApplication(application: Application) {
        this.application = application
    }

    private fun getTrackRepository(): TrackRepository {
        return TrackRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideTrackInteractor(): TrackInteractor {
        return TrackInteractorImpl(getTrackRepository())
    }


    fun provideSearchHistoryRepository(): SearchHistoryRepositoryImpl {
        return SearchHistoryRepositoryImpl(provideSharedPreferences())
    }

    fun provideSharedPreferences(): SharedPreferences {
        return application.getSharedPreferences(PLAYLISTMAKER_PREFERENCES, MODE_PRIVATE)
    }

}