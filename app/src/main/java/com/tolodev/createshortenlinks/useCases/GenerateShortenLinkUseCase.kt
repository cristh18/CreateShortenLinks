package com.tolodev.createshortenlinks.useCases

import com.tolodev.createshortenlinks.data.repositories.RemoteLinkGeneratorRepository
import com.tolodev.createshortenlinks.data.server.models.LinkRequest
import com.tolodev.createshortenlinks.ui.models.GenericItem.UIShortenLink
import com.tolodev.createshortenlinks.ui.models.toUIShortenLink
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GenerateShortenLinkUseCase @Inject constructor(private val repository: RemoteLinkGeneratorRepository) {

    operator fun invoke(link: String): Flow<UIShortenLink> = flow {
        val shortenLink = repository.generateShortenLink(LinkRequest(link))
        val uiShortenLink = shortenLink.toUIShortenLink()
        emit(uiShortenLink)
    }
}