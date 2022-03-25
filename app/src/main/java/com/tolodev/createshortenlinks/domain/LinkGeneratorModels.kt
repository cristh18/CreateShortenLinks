package com.tolodev.createshortenlinks.domain

data class ShortenLink(
    val id: String,
    val linkMetadata: LinkMetadata
)

data class LinkMetadata(
    val originalLink: String,
    val shortLink: String
)