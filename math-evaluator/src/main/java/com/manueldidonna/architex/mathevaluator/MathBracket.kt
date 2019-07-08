package com.manueldidonna.architex.mathevaluator

import com.manueldidonna.architex.mathevaluator.internal.Bracket
import com.manueldidonna.architex.mathevaluator.internal.CloseBracket
import com.manueldidonna.architex.mathevaluator.internal.OpenBracket

const val OPEN_BRACKET_SYMBOL = "("
const val OPEN_BRACKET_SYMBOL_CHAR = '('
const val CLOSE_BRACKET_SYMBOL = ")"
const val CLOSE_BRACKET_SYMBOL_CHAR = ')'

enum class MathBracket(internal val value: () -> Bracket) {
    OPEN({ OpenBracket() }),
    CLOSE({ CloseBracket() })
}