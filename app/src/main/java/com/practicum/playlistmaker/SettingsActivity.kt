package com.practicum.playlistmaker

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar

class SettingsActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val backButton = findViewById<Toolbar>(R.id.toolbar)
        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        val swDarkTheme = findViewById<SwitchCompat>(R.id.night_Mode)
        swDarkTheme.setOnClickListener {
            if (swDarkTheme.isChecked)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        val lineShare = findViewById<FrameLayout>(R.id.share)
        lineShare.setOnClickListener {
            val share = Intent.createChooser(Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, getString(R.string.Practicum_Web))
                setType("text/plain")
            }, null)
            startActivity(share)
        }
        val lineSupport = findViewById<FrameLayout>(R.id.support)
        lineSupport.setOnClickListener {
            val share = Intent.createChooser(Intent().apply {
                action = Intent.ACTION_SENDTO
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.support_contact)))
                putExtra(Intent.EXTRA_TITLE, getString(R.string.support_Title))
                putExtra(Intent.EXTRA_TEXT, getString(R.string.sup_text))
            }, null)
            startActivity(share)
        }
        val lineAgreement = findViewById<FrameLayout>(R.id.agreement)
        lineAgreement.setOnClickListener {
            val share = Intent.createChooser(Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(getString(R.string.Agreement_link))
            }, null)
            startActivity(share)
        }
    }
}