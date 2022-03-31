package com.tolodev.createshortenlinks.useCases

import com.tolodev.createshortenlinks.data.repositories.LocalLinkGeneratorRepository
import com.tolodev.createshortenlinks.ui.models.GenericItem.UIShortenLink
import com.tolodev.createshortenlinks.ui.models.toUIShortenLink
import javax.inject.Inject

class GetShortenLinkByIdUseCase @Inject constructor(private val repository: LocalLinkGeneratorRepository) {

    operator fun invoke(linkId: String): UIShortenLink? {
        return repository.getShortenLinkById(linkId)?.toUIShortenLink()
    }
}