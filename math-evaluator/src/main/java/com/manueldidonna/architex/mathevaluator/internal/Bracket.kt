package com.manueldidonna.architex.mathevaluator.internal

internal const val OPEN_BRACKET_SYMBOL = '('
internal const val CLOSE_BRACKET_SYMBOL = ')'

internal interface Bracket : MathElement

internal class OpenBracket : Bracket {
    override val value = "("
}

internal class CloseBracket : Bracket {
    override val value = ")"
}