package com.practicum.playlistmaker.search.ui


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.search.domain.api.SearchHistoryInteractor
import com.practicum.playlistmaker.search.domain.api.TrackInteractor
import com.practicum.playlistmaker.search.domain.model.SearchResult
import com.practicum.playlistmaker.search.domain.model.Track
import com.practicum.playlistmaker.utils.debounce
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchViewModel(
    application: Application,
    private val trackInteractor: TrackInteractor,
    private val searchHistorySaver: SearchHistoryInteractor,
) : AndroidViewModel(application) {
    private var latestSearchText: String? = null
    private var searchJob: Job? = null

    private val searchState = MutableLiveData<SearchState>()
    fun observeSearchState(): LiveData<SearchState> = searchState

    fun addToHistory(track: Track) {
        searchHistorySaver.addToHistory(track)
        updateHistory()
    }

    fun clearHistory() {
        searchHistorySaver.clearHistory()
        updateHistory()
    }

    fun updateHistory() {
        viewModelScope.launch {
            searchHistorySaver.getHistory().collect { tracks ->
                renderState(SearchState.ContentHistory(tracks))
            }
        }
    }

    private fun renderState(state: SearchState) {
        searchState.postValue(state)
    }

    init {
        updateHistory()
    }

    fun stopSearch() {
        searchJob?.cancel()
        searchJob = null
    }

    fun searchRequest(newSearchText: String) {
        renderState(SearchState.Loading)
        searchJob = viewModelScope.launch {
            trackInteractor.search(newSearchText)
                .collect { result ->
                    processResult(
                        result
                    )
                }
        }
    }

    private fun processResult(searchResult: SearchResult) {
        when (searchResult) {
            is SearchResult.Success -> {
                val tracks = searchResult.tracks
                if (tracks.isNullOrEmpty()) {
                    renderState(SearchState.NothingFound)
                } else if (latestSearchText == searchResult.expression) {
                    renderState(SearchState.ContentSearch(tracks))
                }
            }

            is SearchResult.Error -> {
                val errorMessage = searchResult.message ?: getApplication<Application>().getString(
                    R.string.connect_error
                )

                renderState(SearchState.Error(errorMessage))
            }

        }
    }

    private val trackSearchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY_MILLIS, viewModelScope, true) { changedText ->
            searchRequest(changedText)
        }

    fun searchDebounce(changedText: String) {
        stopSearch()
        if (latestSearchText == changedText || changedText.isEmpty() || changedText.length < 2) {
            return
        }
        latestSearchText = changedText
        trackSearchDebounce(changedText)
    }

    fun updateSearch() {
        searchRequest(latestSearchText ?: "")
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY_MILLIS = 1000L
    }
}