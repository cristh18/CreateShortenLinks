package com.tolodev.createshortenlinks.data.server

import com.tolodev.createshortenlinks.data.server.models.LinkGeneratorResponse
import com.tolodev.createshortenlinks.data.server.models.LinkRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface LinkGeneratorService {

    @POST("api/alias")
    suspend fun generateShortenLink(@Body linkRequest: LinkRequest): LinkGeneratorResponse
}