package com.practicum.playlistmaker.recycleView

import com.practicum.playlistmaker.Track

fun interface OnItemClickListener {
    fun onItemClick(item: Track)
}