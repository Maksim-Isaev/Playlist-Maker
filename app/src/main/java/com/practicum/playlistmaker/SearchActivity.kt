package com.practicum.playlistmaker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search) // изменено на activity_search
        val backButton = findViewById<Toolbar>(R.id.toolbar)
        backButton.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
