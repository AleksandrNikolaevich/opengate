package ru.kode.tools.opengate.foundation.core

interface SecurityStorage {
    fun set(key: String, value: String): Boolean

    fun get(forKey: String): String?

    fun remove(forKey: String): Boolean

    fun clearAll(): Boolean

    fun getAllKeys(): List<String>
}