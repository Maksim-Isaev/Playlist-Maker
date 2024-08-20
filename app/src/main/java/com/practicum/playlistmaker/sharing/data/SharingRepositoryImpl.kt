package com.practicum.playlistmaker.sharing.data

import android.content.Context
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.sharing.domain.api.SharingRepository
import com.practicum.playlistmaker.sharing.domain.model.MailData
import com.practicum.playlistmaker.sharing.domain.model.ShareData
import com.practicum.playlistmaker.sharing.domain.model.TermsData

class SharingRepositoryImpl(private val context: Context) : SharingRepository {
    override fun getShareData(): ShareData {
        return ShareData(
            url = context.getString(R.string.practicum_Web),
            title = context.getString(R.string.practikumHeader)
        )
    }

    override fun getMailData(): MailData {
        return MailData(
            mail = context.getString(R.string.supportMail),
            subject = context.getString(R.string.suportSubject),
            text = context.getString(R.string.supportText),
            title = context.getString(R.string.practikumHeader)
        )
    }

    override fun getTermsData(): TermsData {
        return TermsData(
            link = context.getString(R.string.termsLink),
        )
    }
}
