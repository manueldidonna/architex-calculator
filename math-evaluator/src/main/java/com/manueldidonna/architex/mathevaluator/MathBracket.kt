package com.manueldidonna.architex.mathevaluator

internal const val OPEN_BRACKET_SYMBOL = '('
internal const val CLOSE_BRACKET_SYMBOL = ')'

enum class MathBracket(internal val value: Char) {
    OPEN(OPEN_BRACKET_SYMBOL), CLOSE(CLOSE_BRACKET_SYMBOL)
}