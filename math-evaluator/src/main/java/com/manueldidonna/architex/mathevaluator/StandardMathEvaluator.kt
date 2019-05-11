package com.manueldidonna.architex.mathevaluator

import net.objecthunter.exp4j.ExpressionBuilder

class StandardMathEvaluator : MathEvaluator {

    override fun eval(expr: String): EvaluationResult = try {
        val result = ExpressionBuilder(expr.sanitizeForEvaluation()).build().evaluate()
        EvaluationResult.Success(result)
    } catch (e: Exception) {
        EvaluationResult.Error(e)
    }
}