package com.practicum.playlistmaker.settings.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.ActivitySettingsBinding
import com.practicum.playlistmaker.sharing.domain.model.MailData
import com.practicum.playlistmaker.sharing.domain.model.ShareData
import com.practicum.playlistmaker.sharing.domain.model.TermsData
import com.practicum.playlistmaker.utils.App


class SettingsActivity : AppCompatActivity() {
    private val binding: ActivitySettingsBinding by lazy {
        ActivitySettingsBinding.inflate(layoutInflater)
    }


    private lateinit var viewModel: SettingsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        viewModel = ViewModelProvider(
            this,
            SettingsViewModel.getViewModelFactory()
        )[SettingsViewModel::class.java]

        binding.darkTheme.isChecked = viewModel.observeThemeState().value!!
        binding.darkTheme.setOnCheckedChangeListener { _, checked ->
            (applicationContext as App).switchTheme(checked)
            viewModel.updateThemeState(checked)
        }

        binding.share.setOnClickListener {
            viewModel.observeShareState().observe(this) { sData ->
                shareTo(sData)
            }
        }

        binding.support.setOnClickListener {
            viewModel.observeSupportState().observe(this) { mData ->
                supportTo(mData)
            }
        }

        binding.terms.setOnClickListener {
            viewModel.observeTermsState().observe(this) { tData ->
                termsTo(tData)
            }
        }
    }

    private fun shareTo(data: ShareData) {
            val share = Intent.createChooser(Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, data.url)
                setType("text/plain")
                putExtra(Intent.EXTRA_TITLE, data.title)
            }, null)
            startActivity(share)
        }

        private fun supportTo(mData: MailData) {
            val share = Intent.createChooser(Intent().apply {
                action = Intent.ACTION_SENDTO
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(mData.mail))
                putExtra(Intent.EXTRA_SUBJECT, mData.subject)
                putExtra(Intent.EXTRA_TEXT, mData.text)
            }, mData.title)
            startActivity(share)
        }


        private fun termsTo(tData: TermsData) {
            val share = Intent.createChooser(Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(tData.link)
            }, null)
            startActivity(share)
        }
    }
}