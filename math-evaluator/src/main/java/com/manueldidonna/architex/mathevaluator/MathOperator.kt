package com.manueldidonna.architex.mathevaluator

internal const val PLUS_SYMBOL = '+'
internal const val MINUS_SYMBOL = '+'
internal const val MULTIPLY_SYMBOL = '×'
internal const val DIVIDE_SYMBOL = '÷'

enum class MathOperator(internal val value: Char) {
    PLUS_MINUS('±'),
    PLUS(PLUS_SYMBOL),
    MINUS(MINUS_SYMBOL),
    MULTIPLY(MULTIPLY_SYMBOL),
    DIVIDE(DIVIDE_SYMBOL)
}