package com.tolodev.createshortenlinks.useCases

import com.tolodev.createshortenlinks.data.repositories.LocalLinkGeneratorRepository
import com.tolodev.createshortenlinks.data.repositories.RemoteLinkGeneratorRepository
import com.tolodev.createshortenlinks.data.server.models.LinkRequest
import com.tolodev.createshortenlinks.data.toDomainShortenLink
import com.tolodev.createshortenlinks.domain.ShortenLinkResult
import com.tolodev.createshortenlinks.extensions.readFromJsonFile
import com.tolodev.createshortenlinks.ui.models.toUIShortenLink
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GenerateShortenLinkUseCaseTest {

    @MockK
    private lateinit var remoteRepository: RemoteLinkGeneratorRepository

    @MockK
    private lateinit var localRepository: LocalLinkGeneratorRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `generate shorten link successfully`() {
        val linkId = "70993"
        val originalLink = "https://plugins.jetbrains.com/plugin/10761-detekt"
        val linkRequest = LinkRequest(originalLink)
        val serverShortenLink = readFromJsonFile("link_generator.json")
        serverShortenLink?.let {
            val shortenLink = it.toDomainShortenLink()
            val uiShortenLink = shortenLink.toUIShortenLink()

            coEvery { remoteRepository.generateShortenLink(linkRequest) } returns shortenLink
            every { localRepository.saveShortenLink(shortenLink) } returns Unit
            every { localRepository.getShortenLinkById(linkId) } returns shortenLink
            every { localRepository.getLastShortenedLink() } returns shortenLink

            val useCase = GenerateShortenLinkUseCase(remoteRepository, localRepository)

            runTest {
                val shortenLinkGenerated = useCase.invoke(originalLink).first()
                if (shortenLinkGenerated is ShortenLinkResult.Success) {
                    assertEquals(shortenLinkGenerated.data, uiShortenLink)
                }
            }
        }
    }
}