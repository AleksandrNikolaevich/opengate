package ru.kode.tools.opengate.foundation

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform