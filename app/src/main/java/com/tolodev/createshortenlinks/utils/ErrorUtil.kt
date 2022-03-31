package com.tolodev.createshortenlinks.utils

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import retrofit2.HttpException
import java.nio.charset.Charset

fun getHttpErrorMessage(throwable: Throwable): String {
    return if (throwable is HttpException) {
        throwable.response()?.let { response ->
            val copyException = HttpException(response)
            val responseBody = copyException.response()?.errorBody()
            getBody(responseBody)?.let {
                val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
                val jsonAdapter: JsonAdapter<Any> =
                    moshi.adapter(Any::class.java)

                val responseError = jsonAdapter.fromJson(it)
                responseError?.toString() ?: "Has occurred an error"
            } ?: "Has occurred an error"
        } ?: "Has occurred an error"
    } else {
        "Has occurred an error"
    }
}

private fun getBody(response: ResponseBody?): String? {
    val source: BufferedSource? = response?.source()
    source?.request(Long.MAX_VALUE)
    val buffer: Buffer? = source?.buffer
    return buffer?.clone()?.readString(Charset.forName("UTF-8"))
}