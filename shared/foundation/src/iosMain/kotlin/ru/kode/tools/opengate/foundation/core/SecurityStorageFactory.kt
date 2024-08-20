package ru.kode.tools.opengate.foundation.core

import com.liftric.kvault.KVault
import ru.kode.tools.opengate.foundation.core.SecurityStorage

actual class SecurityStorageFactory(private val serviceName: String, private val accessGroup: String?) {
    private val store = KVault(serviceName, accessGroup)

    actual fun create(): SecurityStorage {
        return object : SecurityStorage {
            override fun set(key: String, value: String): Boolean {
                return store.set(key = key, stringValue = value)
            }

            override fun get(forKey: String): String? {
                return store.string(forKey)
            }

            override fun remove(forKey: String): Boolean {
                return store.deleteObject(forKey)
            }

            override fun clearAll(): Boolean {
                return store.clear()
            }

            override fun getAllKeys(): List<String> {
                return store.allKeys()
            }
        }
    }
}