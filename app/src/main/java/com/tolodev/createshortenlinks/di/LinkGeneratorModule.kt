package com.tolodev.createshortenlinks.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tolodev.createshortenlinks.BuildConfig
import com.tolodev.createshortenlinks.data.datasources.LinkGeneratorServerDataSource
import com.tolodev.createshortenlinks.data.datasources.RemoteDataSource
import com.tolodev.createshortenlinks.data.server.LinkGeneratorService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LinkGeneratorModule {

    private const val TIME_OUT = 20

    @Provides
    @LinkGeneratorBaseUrl
    fun getLinkGeneratorBaseUrl(): String {
        return "https://url-shortener-nu.herokuapp.com"
    }

    @Provides
    @Singleton
    @LinkGeneratorDataService
    fun retrofitLinkGeneratorAuthenticator(
        @LinkGeneratorHttpClientBuilder
        builder: OkHttpClient.Builder,
        @LinkGeneratorSerializer
        moshi: Moshi,
        @LinkGeneratorBaseUrl
        baseUrl: String
    ): Retrofit {
        return getRetrofitBuilder(
            builder.build(),
            baseUrl,
            moshi
        ).build()
    }

    @Provides
    @Singleton
    @LinkGeneratorHttpClientBuilder
    fun getAuthorizationHttpClientBuilder(
        @LinkGeneratorInterceptors
        interceptor: Interceptor
    ): OkHttpClient.Builder {
        return getHttpClientBuilder(interceptor)
    }

    @Provides
    @Singleton
    @LinkGeneratorInterceptors
    fun provideDynamicHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val headers = Headers.Builder()
            val newBuilder = chain.request()
                .newBuilder()
                .headers(headers.build())
                .method(chain.request().method, chain.request().body)

            chain.proceed(newBuilder.build())
        }
    }

    @Provides
    @Singleton
    @LinkGeneratorSerializer
    fun getMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideLinkGeneratorService(@LinkGeneratorDataService retrofit: Retrofit): LinkGeneratorService =
        retrofit.create(LinkGeneratorService::class.java)

    @Provides
    @Singleton
    fun provideLinkGeneratorRemoteDataSource(
        linkGeneratorService: LinkGeneratorService
    ): RemoteDataSource =
        LinkGeneratorServerDataSource(linkGeneratorService)

    private fun getRetrofitBuilder(
        httpClient: OkHttpClient,
        url: String,
        moshi: Moshi
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
    }

    private fun getHttpClientBuilder(
        vararg interceptor: Interceptor
    ): OkHttpClient.Builder {

        val clientBuilder = OkHttpClient.Builder()
            .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)

        interceptor.forEach { clientBuilder.addInterceptor(it) }

        if (BuildConfig.DEBUG) {

            val logging = HttpLoggingInterceptor()

            logging.level = HttpLoggingInterceptor.Level.BODY

            clientBuilder.addInterceptor(logging)
        }

        return clientBuilder
    }
}
