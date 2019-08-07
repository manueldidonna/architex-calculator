package com.manueldidonna.architex.mathevaluator.utils

import java.util.*

inline fun <reified E> ArrayDeque<E>.safeLast(): E? {
    return if (size > 0) last else null
}

inline fun <reified E> ArrayDeque<E>.replaceLast(newElement: E) {
    removeLast()
    add(newElement)
}
