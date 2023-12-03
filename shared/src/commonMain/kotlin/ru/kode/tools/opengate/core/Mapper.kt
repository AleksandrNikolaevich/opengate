package ru.kode.tools.opengate.core

interface Mapper<From, To> {
    fun map(item: From): To
    fun map(list: List<From>): List<To> {
        return list.map { map(it) }
    }
}