package com.practicum.playlistmaker.media.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.media.domain.api.FavoritesInteractor
import com.practicum.playlistmaker.search.domain.model.Track
import kotlinx.coroutines.launch

class FavoritesViewModel(private val favoritesInteractor: FavoritesInteractor) : ViewModel() {

    private val favoriteTracks = MutableLiveData<List<Track>>()
    fun getFavoriteTracks(): LiveData<List<Track>> = favoriteTracks

    fun refreshFavoriteTracks() {
        viewModelScope.launch {
            favoritesInteractor.getFavoriteTracks().collect { trackList ->
                favoriteTracks.postValue(trackList)
            }
        }
    }

    init {
        refreshFavoriteTracks()
    }
}