package ru.kode.tools.opengate.foundation.core

import dev.icerock.moko.mvvm.flow.CStateFlow
import dev.icerock.moko.mvvm.flow.cStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

fun <T, M> CStateFlow<T>.map(
    coroutineScope : CoroutineScope,
    mapper : (value : T) -> M
) : CStateFlow<M> = map { mapper(it) }.stateIn(
    coroutineScope,
    SharingStarted.Eagerly,
    mapper(value)
).cStateFlow()
