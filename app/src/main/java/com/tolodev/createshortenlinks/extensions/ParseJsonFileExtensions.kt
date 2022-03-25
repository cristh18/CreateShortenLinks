package com.tolodev.createshortenlinks.extensions

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tolodev.createshortenlinks.data.server.models.LinkGeneratorResponse

fun Any.readFromJsonFile(fileName: String): LinkGeneratorResponse? {
    return javaClass.classLoader
        ?.getResource("json.mocks/$fileName")
        ?.readText()
        ?.let {
            getMoshiAdapter()?.fromJson(it)
        }
}

private fun getMoshiAdapter(): JsonAdapter<LinkGeneratorResponse>? {
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    return moshi.adapter(LinkGeneratorResponse::class.java)
}