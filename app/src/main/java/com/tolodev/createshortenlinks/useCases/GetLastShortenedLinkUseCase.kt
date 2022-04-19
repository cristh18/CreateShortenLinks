package com.tolodev.createshortenlinks.useCases

import com.tolodev.createshortenlinks.data.repositories.LocalLinkGeneratorRepository
import com.tolodev.createshortenlinks.ui.models.GenericItem
import com.tolodev.createshortenlinks.ui.models.toUIShortenLink
import javax.inject.Inject

class GetLastShortenedLinkUseCase @Inject constructor(private val repository: LocalLinkGeneratorRepository) {

    operator fun invoke(): GenericItem.UIShortenLink? {
        return repository.getLastShortenedLink()?.toUIShortenLink()
    }
}