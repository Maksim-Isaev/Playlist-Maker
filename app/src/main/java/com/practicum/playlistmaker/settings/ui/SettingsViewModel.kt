package com.practicum.playlistmaker.settings.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmaker.settings.domain.MainThemeInteractor
import com.practicum.playlistmaker.sharing.domain.api.SharingRepository
import com.practicum.playlistmaker.sharing.domain.model.MailData
import com.practicum.playlistmaker.sharing.domain.model.ShareData
import com.practicum.playlistmaker.sharing.domain.model.TermsData
import com.practicum.playlistmaker.utils.Creator
import com.practicum.playlistmaker.utils.SingleLiveEvent

class SettingsViewModel(
    sharingRepository: SharingRepository,
    private val themeInteractor: MainThemeInteractor,
) : ViewModel() {
    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SettingsViewModel(
                    Creator.provideSharingRepositoryInteractor(),
                    Creator.provideMainThemeInteractor()
                )
            }
        }
    }

    private val termsState = SingleLiveEvent<TermsData>()
    private val shareState = SingleLiveEvent<ShareData>()
    private val supportState = SingleLiveEvent<MailData>()

    init {
        termsState.postValue(sharingRepository.getTermsData())
        shareState.postValue(sharingRepository.getShareData())
        supportState.postValue(sharingRepository.getMailData())
    }

    fun observeTermsState(): LiveData<TermsData> = termsState
    fun observeShareState(): LiveData<ShareData> = shareState
    fun observeSupportState(): LiveData<MailData> = supportState

    private val isNightThemeEnabled = MutableLiveData(themeInteractor.isNightTheme())
    fun updateThemeState(isNightTheme: Boolean) {
        themeInteractor.saveTheme(isNightTheme)
        isNightThemeEnabled.postValue(isNightTheme)
    }

    fun observeThemeState(): LiveData<Boolean> = isNightThemeEnabled
}