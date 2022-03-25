package com.tolodev.createshortenlinks.data.server.models

import com.squareup.moshi.Json

data class LinkGeneratorResponse(
    @Json(name = "alias")
    val alias: String,
    @Json(name = "_links")
    val links: Link
)

data class Link(
    @Json(name = "self")
    val self: String,
    @Json(name = "short")
    val short: String
)

data class LinkRequest(
    @Json(name = "url")
    val url: String
)