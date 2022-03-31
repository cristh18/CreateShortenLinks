package com.tolodev.createshortenlinks.data.datasources

import com.tolodev.createshortenlinks.data.server.models.LinkGeneratorResponse
import com.tolodev.createshortenlinks.data.server.models.LinkRequest

interface RemoteDataSource {
    suspend fun generateShortenLink(linkRequest: LinkRequest): LinkGeneratorResponse
}
