package com.manueldidonna.architex.mathevaluator

import com.manueldidonna.architex.mathevaluator.internal.DivideOperator
import com.manueldidonna.architex.mathevaluator.internal.MinusOperator
import com.manueldidonna.architex.mathevaluator.internal.MultiplyOperator
import com.manueldidonna.architex.mathevaluator.internal.Operator
import com.manueldidonna.architex.mathevaluator.internal.PlusOperator

enum class MathOperator(internal val value: () -> Operator) {
    PLUS_MINUS({ PlusOperator() }),
    PLUS({ PlusOperator() }),
    MINUS({ MinusOperator() }),
    MULTIPLY({ MultiplyOperator() }),
    DIVIDE({ DivideOperator() })
}