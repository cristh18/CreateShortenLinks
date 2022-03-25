package com.tolodev.createshortenlinks.useCases

import com.tolodev.createshortenlinks.data.repositories.RemoteLinkGeneratorRepository
import com.tolodev.createshortenlinks.data.server.models.LinkRequest
import com.tolodev.createshortenlinks.data.toDomainShortenLink
import com.tolodev.createshortenlinks.extensions.readFromJsonFile
import com.tolodev.createshortenlinks.ui.models.toUIShortenLink
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GenerateShortenLinkUseCaseTest {

    @MockK
    private lateinit var repository: RemoteLinkGeneratorRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `generate shorten link successfully`() {
        val originalLink = "https://plugins.jetbrains.com/plugin/10761-detekt"
        val linkRequest = LinkRequest(originalLink)
        val serverShortenLink = readFromJsonFile("link_generator.json")
        serverShortenLink?.let {
            val shortenLink = it.toDomainShortenLink()
            val uiShortenLink = shortenLink.toUIShortenLink()

            coEvery { repository.generateShortenLink(linkRequest) } returns shortenLink

            val useCase = GenerateShortenLinkUseCase(repository)

            runBlockingTest {
                val shortenLinkGenerated = useCase.invoke(originalLink).first()
                assertEquals(shortenLinkGenerated, uiShortenLink)
            }
        }
    }
}