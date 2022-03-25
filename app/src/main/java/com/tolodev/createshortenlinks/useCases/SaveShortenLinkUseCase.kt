package com.tolodev.createshortenlinks.useCases

import com.tolodev.createshortenlinks.data.repositories.LocalLinkGeneratorRepository
import com.tolodev.createshortenlinks.domain.toDomainShortenLink
import com.tolodev.createshortenlinks.ui.models.GenericItem.UIShortenLink
import javax.inject.Inject

class SaveShortenLinkUseCase @Inject constructor(private val repository: LocalLinkGeneratorRepository) {

    operator fun invoke(uiShortenLink: UIShortenLink) {
        val shortenLink = uiShortenLink.toDomainShortenLink()
        repository.saveShortenLink(shortenLink)
    }
}