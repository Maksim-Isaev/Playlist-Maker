package com.practicum.playlistmaker.sharing.domain.api

import com.practicum.playlistmaker.sharing.domain.model.MailData
import com.practicum.playlistmaker.sharing.domain.model.ShareData
import com.practicum.playlistmaker.sharing.domain.model.TermsData

interface SharingRepository {
    fun getShareData(): ShareData
    fun getMailData(): MailData
    fun getTermsData(): TermsData
}
