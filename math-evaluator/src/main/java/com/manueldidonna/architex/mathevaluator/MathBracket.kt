package com.manueldidonna.architex.mathevaluator

const val OPEN_BRACKET_SYMBOL = "("
const val OPEN_BRACKET_SYMBOL_CHAR = '('
const val CLOSE_BRACKET_SYMBOL = ")"
const val CLOSE_BRACKET_SYMBOL_CHAR = ')'

internal interface HasOpenBracket

internal interface Bracket : MathElement

internal object OpenBracket : Bracket, HasOpenBracket {
    override val value = OPEN_BRACKET_SYMBOL
}

internal object CloseBracket : Bracket {
    override val value = CLOSE_BRACKET_SYMBOL
}

enum class MathBracket(internal val value: Bracket) {
    OPEN(OpenBracket),
    CLOSE(CloseBracket)
}