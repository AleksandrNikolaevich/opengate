package ru.kode.tools.opengate.foundation.core

import dev.icerock.moko.mvvm.flow.CStateFlow
import dev.icerock.moko.mvvm.flow.cStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

fun <T, M, R> CStateFlow<T>.combine(
    with: StateFlow<M>,
    coroutineScope : CoroutineScope,
    mapper : (value : T, value2 : M) -> R
) : CStateFlow<R> = combine(with) { val1, val2 -> mapper(val1, val2) }
    .stateIn(
        coroutineScope,
        SharingStarted.Eagerly,
        mapper(value, with.value)
    )
    .cStateFlow()