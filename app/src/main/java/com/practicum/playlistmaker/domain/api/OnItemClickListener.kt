package com.practicum.playlistmaker.domain.api

import com.practicum.playlistmaker.domain.model.Track

fun interface OnItemClickListener {
    fun onItemClick(item: Track)
}