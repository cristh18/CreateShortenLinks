package com.tolodev.createshortenlinks.data.repositories

import com.tolodev.createshortenlinks.domain.ShortenLink

interface LocalRepository {

    fun saveShortenLink(shortenLink: ShortenLink)

    fun getShortenLinkById(shortenLinkId: String): ShortenLink?

    fun getShortenLinkList(): Set<ShortenLink>

    fun deleteAllShortenLinks()

    fun getLastShortenedLink(): ShortenLink?
}