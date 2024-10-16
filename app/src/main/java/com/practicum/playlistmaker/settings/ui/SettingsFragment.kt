package com.practicum.playlistmaker.settings.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.FragmentSettingsBinding
import com.practicum.playlistmaker.sharing.domain.model.MailData
import com.practicum.playlistmaker.sharing.domain.model.ShareData
import com.practicum.playlistmaker.sharing.domain.model.TermsData
import com.practicum.playlistmaker.utils.App

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<SettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.darkTheme.isChecked = viewModel.observeThemeState().value!!
        binding.darkTheme.setOnCheckedChangeListener { _, checked ->
            (requireActivity().application as App).switchTheme(checked)
            viewModel.updateThemeState(checked)
        }
        binding.share.setOnClickListener {
            shareTo(viewModel.getShareData())
        }

        binding.support.setOnClickListener {
            supportTo(viewModel.getMailData())
        }

        binding.terms.setOnClickListener {
            termsTo(viewModel.getTermsData())
        }
    }

    private fun termsTo(tData: TermsData) {
        val share = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(tData.link)
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
        try {
            startActivity(share)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(context, R.string.application_not_found, Toast.LENGTH_SHORT).show()
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
}