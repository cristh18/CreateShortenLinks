package com.tolodev.createshortenlinks.data.datasources

import com.tolodev.createshortenlinks.domain.ShortenLink

interface LocalDataSource {

    fun saveShortenLink(shortenLink: ShortenLink)

    fun getShortenLinkById(shortenLinkId: String): ShortenLink?

    fun getShortenLinkList(): List<ShortenLink>

    fun deleteAllShortenLinks()
}