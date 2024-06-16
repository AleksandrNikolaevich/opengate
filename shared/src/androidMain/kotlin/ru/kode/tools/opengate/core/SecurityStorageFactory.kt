package ru.kode.tools.opengate.core;

import android.content.Context
import com.liftric.kvault.KVault

actual class SecurityStorageFactory(private val context: Context, private val fileName: String) {
    private val store = KVault(context, fileName)

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
