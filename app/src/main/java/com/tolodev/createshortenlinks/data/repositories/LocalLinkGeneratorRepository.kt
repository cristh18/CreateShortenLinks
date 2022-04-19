package com.tolodev.createshortenlinks.data.repositories

import com.tolodev.createshortenlinks.data.datasources.LinkGeneratorDBDataSource
import com.tolodev.createshortenlinks.domain.ShortenLink
import javax.inject.Inject

class LocalLinkGeneratorRepository @Inject constructor(private val dataSource: LinkGeneratorDBDataSource) :
    LocalRepository {

    override fun saveShortenLink(shortenLink: ShortenLink) {
        dataSource.saveShortenLink(shortenLink)
    }

    override fun getShortenLinkById(shortenLinkId: String): ShortenLink? {
        return dataSource.getShortenLinkById(shortenLinkId)
    }

    override fun getShortenLinkList(): Set<ShortenLink> {
        return dataSource.getShortenLinkList()
    }

    override fun deleteAllShortenLinks() {
        dataSource.deleteAllShortenLinks()
    }

    override fun getLastShortenedLink(): ShortenLink? {
        return dataSource.getLastShortenedLink()
    }
}