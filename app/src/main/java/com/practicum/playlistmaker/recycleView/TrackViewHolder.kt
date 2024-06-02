package com.practicum.playlistmaker.recycleView

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.Track
import java.util.Locale

// ViewHolder для отображения отдельного элемента трека в RecyclerView
class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    // Определение и инициализация элементов интерфейса для отображения информации о треке
    private val trackName: TextView = itemView.findViewById(R.id.track_name)
    private val artistName: TextView = itemView.findViewById(R.id.artist_name)
    private val trackTime: TextView = itemView.findViewById(R.id.track_time)
    private val albumImage: ImageView = itemView.findViewById(R.id.albumImage)

    // Метод для привязки данных трека к элементам интерфейса
    fun bind(model: Track) {
        // Установка имени трека
        trackName.text = model.trackName
        // Установка исполнителя
        artistName.text = model.artistName
        // Форматирование и установка времени трека
        trackTime.text =
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(model.trackTimeMillis)
        // Загрузка изображения альбома с использованием библиотеки Glide
        Glide.with(itemView)
            .load(model.artworkUrl100)
            .placeholder(R.drawable.ic_placeholder) // Плейсхолдер на время загрузки изображения
            .centerCrop()
            .transform(
                RoundedCorners(
                    dpToPx(
                        2f,
                        itemView.context
                    )
                )
            ) // Закругление углов изображения
            .into(albumImage) // Установка изображения в ImageView
    }

    // Преобразование значения из dp в px
    private fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }
}