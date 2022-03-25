package com.tolodev.createshortenlinks.data

import com.tolodev.createshortenlinks.data.server.models.Link
import com.tolodev.createshortenlinks.data.server.models.LinkGeneratorResponse
import com.tolodev.createshortenlinks.domain.LinkMetadata
import com.tolodev.createshortenlinks.domain.ShortenLink

fun LinkGeneratorResponse.toDomainShortenLink(): ShortenLink = ShortenLink(
    alias,
    links.toDomainLinkMetadata()
)

fun Link.toDomainLinkMetadata(): LinkMetadata = LinkMetadata(
    self,
    short
)
