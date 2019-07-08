package com.manueldidonna.architex.mathevaluator

import com.manueldidonna.architex.mathevaluator.internal.*

enum class MathOperator(internal val value: () -> Operator) {
    PLUS_MINUS({ PlusOperator() }),
    PLUS({ PlusOperator() }),
    MINUS({ MinusOperator() }),
    MULTIPLY({ MultiplyOperator() }),
    DIVIDE({ DivideOperator() }),
    EXP({ ExpOperator() })
}