package com.tolodev.createshortenlinks.data.datasources

import com.tolodev.createshortenlinks.data.server.LinkGeneratorService
import com.tolodev.createshortenlinks.data.server.models.LinkGeneratorResponse
import com.tolodev.createshortenlinks.data.server.models.LinkRequest
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

class LinkGeneratorServerDataSource @Inject constructor(
    private val service: LinkGeneratorService
) : RemoteDataSource {

    suspend fun getShortenLink(linkRequest: LinkRequest): LinkGeneratorResponse {
        return withTimeout(30_000) { service.getShortenLink(linkRequest) }
    }
}