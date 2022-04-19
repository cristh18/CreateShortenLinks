package com.tolodev.createshortenlinks.domain

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class ShortenLinkResult<out R> {

    data class Success<out T>(val data: T) : ShortenLinkResult<T>()
    data class Error(val exception: Exception) : ShortenLinkResult<Nothing>()
    object Loading : ShortenLinkResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data].
 */
val ShortenLinkResult<*>.succeeded
    get() = this is ShortenLinkResult.Success && data != null
