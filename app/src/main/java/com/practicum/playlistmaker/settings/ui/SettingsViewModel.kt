package com.practicum.playlistmaker.settings.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.settings.domain.MainThemeInteractor
import com.practicum.playlistmaker.sharing.domain.api.SharingRepository
import com.practicum.playlistmaker.sharing.domain.model.MailData
import com.practicum.playlistmaker.sharing.domain.model.ShareData
import com.practicum.playlistmaker.sharing.domain.model.TermsData

class SettingsViewModel(
    private val application: Application,
    private val themeInteractor: MainThemeInteractor,
) : ViewModel(), SharingRepository {



    private val isNightThemeEnabled = MutableLiveData(themeInteractor.isNightTheme())
    fun updateThemeState(isNightTheme: Boolean) {
        themeInteractor.saveTheme(isNightTheme)
        isNightThemeEnabled.postValue(isNightTheme)
    }

    fun observeThemeState(): LiveData<Boolean> = isNightThemeEnabled

    override fun getShareData(): ShareData {
        return ShareData(
            url = application.getString(R.string.practicum_Web),
            title = application.getString(R.string.practikumHeader)
        )
    }

    override fun getMailData(): MailData {
        return MailData(
            mail = application.getString(R.string.supportMail),
            subject = application.getString(R.string.suportSubject),
            text = application.getString(R.string.supportText),
            title = application.getString(R.string.practikumHeader)
        )
    }

    override fun getTermsData(): TermsData {
        return TermsData(
            link = application.getString(R.string.termsLink),
        )
    }
}