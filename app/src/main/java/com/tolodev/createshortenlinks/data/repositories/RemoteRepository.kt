package com.tolodev.createshortenlinks.data.repositories

import com.tolodev.createshortenlinks.data.server.models.LinkRequest
import com.tolodev.createshortenlinks.domain.ShortenLink

interface RemoteRepository {

    suspend fun generateShortenLink(linkRequest: LinkRequest): ShortenLink
}