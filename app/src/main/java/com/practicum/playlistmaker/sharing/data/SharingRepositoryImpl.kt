package com.practicum.playlistmaker.sharing.data

import android.content.Context
import androidx.core.content.ContextCompat.getString
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.sharing.domain.api.SharingRepository
import com.practicum.playlistmaker.sharing.domain.model.MailData
import com.practicum.playlistmaker.sharing.domain.model.ShareData
import com.practicum.playlistmaker.sharing.domain.model.TermsData

class SharingRepositoryImpl(private val context: Context) : SharingRepository {
    override fun getShareData(): ShareData {
        return ShareData(
            url = getString(context, R.string.practicum_Web),
            title = getString(context, R.string.practikumHeader)
        )
    }

    override fun getMailData(): MailData {
        return MailData(
            mail = getString(context, R.string.support_contact),
            subject = getString(context, R.string.support_Title),
            text = getString(context, R.string.sup_text),
            title = getString(context, R.string.practikumHeader)
        )
    }

    override fun getTermsData(): TermsData {
        return TermsData(
            link = getString(context, R.string.agreement_link),
        )
    }
}