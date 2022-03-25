package com.tolodev.createshortenlinks.ui.models

sealed class UIStatus<out T : Any> {
    data class Loading(val show: Boolean) : UIStatus<Nothing>()
    data class Error(val msg: String, val throwable: Throwable? = null) : UIStatus<Nothing>()
    data class Successful<out T : Any>(val value: T) : UIStatus<T>()
}