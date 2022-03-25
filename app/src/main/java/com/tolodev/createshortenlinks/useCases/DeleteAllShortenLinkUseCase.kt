package com.tolodev.createshortenlinks.useCases

import com.tolodev.createshortenlinks.data.repositories.LocalLinkGeneratorRepository
import javax.inject.Inject

class DeleteAllShortenLinkUseCase @Inject constructor(private val repository: LocalLinkGeneratorRepository) {

    operator fun invoke() {
        repository.deleteAllShortenLinks()
    }
}