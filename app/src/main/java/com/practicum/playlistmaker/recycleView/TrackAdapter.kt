package com.practicum.playlistmaker.recycleView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.Track

// Адаптер для отображения списка треков в RecyclerView

class TrackAdapter(
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<TrackViewHolder>() {
    var items = ArrayList<Track>()

    // Создание нового ViewHolder при необходимости
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        // Машстабирование макета для элемента списка
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_music_search_item, parent, false)
        // Возвращаем новый экземпляр TrackViewHolder
        return TrackViewHolder(view)
    }

    // Возвращает количество элементов в списке треков
    override fun getItemCount(): Int {
        return items.size
    }

    // Привязка данных трека к ViewHolder для отображения
    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        // Передача трека из списка на указанной позиции ViewHolder'у
        holder.bind(items[position])
        holder.itemView.setOnClickListener { onItemClickListener.onItemClick(items[holder.adapterPosition]) }
    }

}
