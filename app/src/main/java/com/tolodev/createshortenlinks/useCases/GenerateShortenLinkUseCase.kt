package com.tolodev.createshortenlinks.useCases

import com.tolodev.createshortenlinks.data.repositories.LinkGeneratorRepository
import com.tolodev.createshortenlinks.data.server.models.LinkRequest
import com.tolodev.createshortenlinks.ui.models.UIShortenLink
import com.tolodev.createshortenlinks.ui.models.toUIShortenLink
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GenerateShortenLinkUseCase @Inject constructor(private val linkGeneratorRepository: LinkGeneratorRepository) {

    operator fun invoke(link: String): Flow<UIShortenLink> = flow {
        val shortenLink = linkGeneratorRepository.getShortenLink(LinkRequest(link))
        val uiShortenLink = shortenLink.toUIShortenLink()
        emit(uiShortenLink)
    }
}