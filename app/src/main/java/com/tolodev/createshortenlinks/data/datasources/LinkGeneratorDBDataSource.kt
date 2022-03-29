package com.tolodev.createshortenlinks.data.datasources

import com.tolodev.createshortenlinks.domain.ShortenLink
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LinkGeneratorDBDataSource @Inject constructor() : LocalDataSource {

    private val shortenLinkList: MutableSet<ShortenLink> = mutableSetOf()

    override fun saveShortenLink(shortenLink: ShortenLink) {
        shortenLinkList += shortenLink
    }

    override fun getShortenLinkById(shortenLinkId: String): ShortenLink? {
        return shortenLinkList.firstOrNull { shortenLinkId.equals(it.id, true) }
    }

    override fun getShortenLinkList(): Set<ShortenLink> {
        return shortenLinkList
    }

    override fun deleteAllShortenLinks() {
        shortenLinkList.clear()
    }
}