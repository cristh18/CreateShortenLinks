package com.tolodev.createshortenlinks.useCases

import com.tolodev.createshortenlinks.data.repositories.LocalLinkGeneratorRepository
import com.tolodev.createshortenlinks.ui.models.GenericItem.UIShortenLink
import com.tolodev.createshortenlinks.ui.models.toUIShortenLink
import javax.inject.Inject

class GetShortenLinkListUseCase @Inject constructor(private val repository: LocalLinkGeneratorRepository) {

    operator fun invoke(): List<UIShortenLink> {
        val shortenLinkList = repository.getShortenLinkList()
        return shortenLinkList.map { it.toUIShortenLink() }
    }
}