package com.tolodev.createshortenlinks.useCases

import com.tolodev.createshortenlinks.data.repositories.LocalLinkGeneratorRepository
import com.tolodev.createshortenlinks.data.repositories.RemoteLinkGeneratorRepository
import com.tolodev.createshortenlinks.data.server.models.LinkRequest
import com.tolodev.createshortenlinks.domain.ShortenLinkResult
import com.tolodev.createshortenlinks.ui.models.GenericItem.UIShortenLink
import com.tolodev.createshortenlinks.ui.models.toUIShortenLink
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GenerateShortenLinkUseCase @Inject constructor(
    private val remoteRepository: RemoteLinkGeneratorRepository,
    private val localRepository: LocalLinkGeneratorRepository
) {

    operator fun invoke(link: String): Flow<ShortenLinkResult<UIShortenLink>> = flow {
        val shortenLink = remoteRepository.generateShortenLink(LinkRequest(link))
        localRepository.saveShortenLink(shortenLink)
        localRepository.getLastShortenedLink()?.toUIShortenLink()
            ?.let { uiShortenLink ->
                emit(ShortenLinkResult.Success(uiShortenLink))
            } ?: emit(ShortenLinkResult.Error(Exception("Link not found")))
    }
}