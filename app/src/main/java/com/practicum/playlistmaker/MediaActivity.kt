package com.practicum.playlistmaker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.practicum.playlistmaker.R


class MediaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)
        val backButton = findViewById<Toolbar>(R.id.toolbar)
        backButton.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }
    }
}