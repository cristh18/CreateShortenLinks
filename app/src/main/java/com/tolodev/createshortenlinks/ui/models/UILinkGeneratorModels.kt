package com.tolodev.createshortenlinks.ui.models

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.android.parcel.Parcelize

sealed class GenericItem {

    @Parcelize
    data class UIHeader(@StringRes val title: Int) : GenericItem(), Parcelable

    @Parcelize
    data class UIShortenLink(
        val id: String,
        val linkMetadata: UILinkMetadata
    ) : GenericItem(), Parcelable
}

@Parcelize
data class UILinkMetadata(
    val originalLink: String,
    val shortLink: String
) : Parcelable

