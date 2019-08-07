package com.manueldidonna.architex.mathevaluator

import com.manueldidonna.architex.mathevaluator.evaluator.MathEvaluator
import com.manueldidonna.architex.mathevaluator.evaluator.MathResult

inline fun MathExpression.buildExpression(sanitize: Boolean, body: MathExpression.() -> Unit): String {
    return apply(body).buildExpression(sanitize)
}

fun MathExpression.eval(evaluator: MathEvaluator): MathResult = evaluator.eval(buildExpression(true))