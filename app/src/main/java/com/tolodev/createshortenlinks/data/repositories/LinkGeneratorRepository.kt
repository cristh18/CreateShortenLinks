package com.tolodev.createshortenlinks.data.repositories

import com.tolodev.createshortenlinks.data.datasources.LinkGeneratorServerDataSource
import com.tolodev.createshortenlinks.data.server.models.LinkRequest
import com.tolodev.createshortenlinks.data.toDomainShortenLink
import com.tolodev.createshortenlinks.domain.ShortenLink
import javax.inject.Inject

class LinkGeneratorRepository @Inject constructor(private val dataSource: LinkGeneratorServerDataSource) {

    suspend fun getShortenLink(linkRequest: LinkRequest): ShortenLink =
        dataSource.getShortenLink(linkRequest).toDomainShortenLink()
}
