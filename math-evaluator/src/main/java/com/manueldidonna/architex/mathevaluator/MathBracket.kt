package com.manueldidonna.architex.mathevaluator

import com.manueldidonna.architex.mathevaluator.internal.Bracket
import com.manueldidonna.architex.mathevaluator.internal.CloseBracket
import com.manueldidonna.architex.mathevaluator.internal.OpenBracket

enum class MathBracket(internal val value: () -> Bracket) {
    OPEN({ OpenBracket() }),
    CLOSE({ CloseBracket() })
}