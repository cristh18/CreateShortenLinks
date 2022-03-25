package com.tolodev.createshortenlinks.ui.models

data class UIShortenLink(
    val id: String,
    val linkMetadata: UILinkMetadata
)

data class UILinkMetadata(
    val originalLink: String,
    val shortLink: String
)

