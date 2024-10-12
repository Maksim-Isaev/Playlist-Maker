package com.practicum.playlistmaker.search.domain.api

import com.practicum.playlistmaker.search.domain.model.SearchResult
import kotlinx.coroutines.flow.Flow

interface TrackInteractor {
    fun search(expression: String): Flow<SearchResult>
}