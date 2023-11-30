package ru.kode.tools.opengate

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform