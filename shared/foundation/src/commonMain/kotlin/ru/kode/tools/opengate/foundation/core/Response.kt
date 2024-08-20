package ru.kode.tools.opengate.foundation.core

sealed interface Response<out T> {
    data class Cached<out R>(val data: R) : Response<R>

    data class Success<out R>(val data: R) : Response<R>

    data class Failed(val throwable: Throwable) : Response<Nothing>
}