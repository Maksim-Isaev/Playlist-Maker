package com.practicum.playlistmaker.search.ui

import com.practicum.playlistmaker.search.domain.model.Track

fun interface OnItemClickListener {
    fun onItemClick(item: Track)
}

fun interface OnItemLongClickListener {
    fun onItemLongClick(item: Track)
}