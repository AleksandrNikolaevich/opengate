package ru.kode.tools.opengate.core

expect class SecurityStorageFactory {
    fun create() : SecurityStorage
}