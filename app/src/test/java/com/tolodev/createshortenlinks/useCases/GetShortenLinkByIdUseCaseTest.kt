package com.tolodev.createshortenlinks.useCases

import com.tolodev.createshortenlinks.data.repositories.LocalLinkGeneratorRepository
import com.tolodev.createshortenlinks.data.toDomainShortenLink
import com.tolodev.createshortenlinks.extensions.readFromJsonFile
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetShortenLinkByIdUseCaseTest {

    @MockK
    private lateinit var repository: LocalLinkGeneratorRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `get shorten link by id successfully`() {
        val linkId = "70993"
        val serverShortenLink = readFromJsonFile("link_generator.json")
        serverShortenLink?.let {
            val shortenLink = it.toDomainShortenLink()

            every { repository.getShortenLinkById(linkId) } returns shortenLink

            val useCase = GetShortenLinkByIdUseCase(repository)

            val foundShortenLink = useCase.invoke(linkId)
            assertEquals(foundShortenLink?.id, linkId)
        }
    }
}