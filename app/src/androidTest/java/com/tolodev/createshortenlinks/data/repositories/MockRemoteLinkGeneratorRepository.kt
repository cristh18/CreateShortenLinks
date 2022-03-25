package com.tolodev.createshortenlinks.data.repositories

import com.tolodev.createshortenlinks.data.server.models.LinkRequest
import com.tolodev.createshortenlinks.domain.LinkMetadata
import com.tolodev.createshortenlinks.domain.ShortenLink

class MockRemoteLinkGeneratorRepository : RemoteRepository {

    override suspend fun generateShortenLink(linkRequest: LinkRequest): ShortenLink {
        return ShortenLink(
            "70993",
            LinkMetadata(
                "https://plugins.jetbrains.com/plugin/10761-detekt",
                "https://url-shortener-nu.herokuapp.com/short/70993"
            )
        )
    }
}