package com.tolodev.createshortenlinks.data.repositories

import com.tolodev.createshortenlinks.domain.ShortenLink
import javax.inject.Singleton

@Singleton
class MockLocalLinkGeneratorRepository : LocalRepository {

    private val mockShortenLinks: MutableList<ShortenLink> = mutableListOf()

    override fun saveShortenLink(shortenLink: ShortenLink) {
        mockShortenLinks += shortenLink
    }

    override fun getShortenLinkById(shortenLinkId: String): ShortenLink? {
        return mockShortenLinks.firstOrNull { shortenLinkId.equals(it.id, true) }
    }

    override fun getShortenLinkList(): List<ShortenLink> {
        return mockShortenLinks
    }

    override fun deleteAllShortenLinks() {
        mockShortenLinks.clear()
    }
}