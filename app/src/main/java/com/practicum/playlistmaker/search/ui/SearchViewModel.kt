package com.practicum.playlistmaker.search.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.search.domain.api.SearchHistoryInteractor
import com.practicum.playlistmaker.search.domain.api.TrackInteractor
import com.practicum.playlistmaker.search.domain.model.Resource
import com.practicum.playlistmaker.search.domain.model.Track
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel(
    application: Application,
    private val trackInteractor: TrackInteractor,
    private val searchHistorySaver: SearchHistoryInteractor,
) :
    AndroidViewModel(application) {
    private var latestSearchText: String? = null
    private var searchJob: Job? = null

    override fun onCleared() {
        stopSearch()
    }

    fun stopSearch() {
        searchJob?.cancel()
    }

    private val searchState = MutableLiveData<SearchState>()
    fun observeSearchState(): LiveData<SearchState> = searchState

    fun addToHistory(track: Track) {
        searchHistorySaver.addToHistory(track)
        renderState(SearchState.ContentHistory(searchHistorySaver.getHistory()))
    }

    fun clearHistory() {
        searchHistorySaver.clearHistory()
        renderState(SearchState.EmptyHistory)
    }

    private fun renderState(state: SearchState) {
        searchState.postValue(state)
    }

    private val consumer = object : TrackInteractor.TrackConsumer {
        override fun consume(foundTracks: Resource<List<Track>>) {
        }

        override fun consume(foundTracks: Resource<List<Track>>, request: String) {
            when (foundTracks) {
                is Resource.Error -> renderState(
                    SearchState.Error(
                        errorMessage = application.getString(
                            R.string.connect_error
                        )
                    )
                )

                is Resource.Success -> {
                    if (foundTracks.data?.isNotEmpty() == true) {
                        if (request == latestSearchText) {
                            renderState(SearchState.ContentSearch(foundTracks.data))
                        }
                    } else {
                        renderState(SearchState.NothingFound)
                    }
                }
            }
        }

        override fun onFailure(t: Throwable) {
            renderState(
                SearchState.Error(
                    errorMessage = application.getString(
                        R.string.connect_error
                    )
                )
            )
        }
    }

    init {
        val searchHistory = searchHistorySaver.getHistory()
        if (searchHistory.isEmpty())
            renderState(SearchState.EmptyHistory)
        else
            renderState(SearchState.ContentHistory(searchHistory))
    }

    fun searchRequest(newSearchText: String) {
        latestSearchText = newSearchText
        renderState(SearchState.Loading)
        trackInteractor
            .search(newSearchText, consumer)
    }

    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY_MILLIS)
            searchRequest(changedText)
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY_MILLIS = 1000L
    }
}
