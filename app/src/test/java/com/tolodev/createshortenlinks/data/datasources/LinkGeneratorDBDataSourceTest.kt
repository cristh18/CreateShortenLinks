package com.tolodev.createshortenlinks.data.datasources

import com.tolodev.createshortenlinks.data.toDomainShortenLink
import com.tolodev.createshortenlinks.extensions.readFromJsonFile
import org.junit.Assert
import org.junit.Test

class LinkGeneratorDBDataSourceTest {

    @Test
    fun deleteAllShortenLinks() {
        val serverShortenLink = readFromJsonFile("link_generator.json")
        serverShortenLink?.let {
            val shortenLink = it.toDomainShortenLink()
            val linkGeneratorDBDataSource = LinkGeneratorDBDataSource()
            linkGeneratorDBDataSource.saveShortenLink(shortenLink)
            linkGeneratorDBDataSource.deleteAllShortenLinks()
            assert(linkGeneratorDBDataSource.getShortenLinkList().isEmpty())
        }
    }

    @Test
    fun saveShortenLink() {
        val serverShortenLink = readFromJsonFile("link_generator.json")
        serverShortenLink?.let {
            val shortenLink = it.toDomainShortenLink()
            val linkGeneratorDBDataSource = LinkGeneratorDBDataSource()
            linkGeneratorDBDataSource.saveShortenLink(shortenLink)
            assert(linkGeneratorDBDataSource.getShortenLinkList().isNotEmpty())
            assert(linkGeneratorDBDataSource.getShortenLinkList().size == 1)
            Assert.assertEquals(linkGeneratorDBDataSource.getShortenLinkList().first().id, shortenLink.id)
        }
    }
}