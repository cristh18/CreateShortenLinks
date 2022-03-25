package com.tolodev.createshortenlinks.di

import javax.inject.Qualifier

@Qualifier
annotation class LinkGeneratorBaseUrl

@Qualifier
annotation class LinkGeneratorSerializer

@Qualifier
annotation class LinkGeneratorHttpClientBuilder

@Qualifier
annotation class LinkGeneratorInterceptors

@Qualifier
annotation class LinkGeneratorDataService
