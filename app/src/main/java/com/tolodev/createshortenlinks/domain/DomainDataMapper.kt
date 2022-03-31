package com.tolodev.createshortenlinks.domain

import com.tolodev.createshortenlinks.ui.models.GenericItem
import com.tolodev.createshortenlinks.ui.models.UILinkMetadata

fun GenericItem.UIShortenLink.toDomainShortenLink(): ShortenLink = ShortenLink(
    id,
    linkMetadata.toDomainLinkMetadata()
)

fun UILinkMetadata.toDomainLinkMetadata(): LinkMetadata = LinkMetadata(
    originalLink,
    shortLink
)