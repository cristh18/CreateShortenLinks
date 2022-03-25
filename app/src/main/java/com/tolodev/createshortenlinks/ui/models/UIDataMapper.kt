package com.tolodev.createshortenlinks.ui.models

import com.tolodev.createshortenlinks.domain.LinkMetadata
import com.tolodev.createshortenlinks.domain.ShortenLink

fun ShortenLink.toUIShortenLink(): GenericItem.UIShortenLink = GenericItem.UIShortenLink(
    id,
    linkMetadata.toUILinkMetadata()
)

fun LinkMetadata.toUILinkMetadata(): UILinkMetadata = UILinkMetadata(
    originalLink,
    shortLink
)