package com.practicum.playlistmaker

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonSearch = findViewById<Button>(R.id.button_search)
        val buttonMedia = findViewById<Button>(R.id.button_media)
        val buttonSettings = findViewById<Button>(R.id.button_settings)

        // Реализация нажатия на первую кнопку (поиск) через лямбда-выражение (скорректированно после 8 спринта)
        buttonSearch.setOnClickListener{
            val intent = Intent(this@MainActivity,SearchActivity::class.java)
            startActivity(intent)
        }

        // Реализация нажатия на вторую кнопку (медиатека) через лямбда-выражение
        buttonMedia.setOnClickListener {
            //Toast.makeText(this@MainActivity, "Button 2 - Лямбда-выражение", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@MainActivity, MediaActivity::class.java)
            startActivity(intent)
        }

        // Реализация нажатия на третью кнопку (настройки) через через лямбда-выражение (скорректированно после 8 спринта)
        buttonSettings.setOnClickListener {
            //Toast.makeText(this@MainActivity, "Button 2 - Лямбда-выражение", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(intent)
            }

    }
}
