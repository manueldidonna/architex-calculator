package com.manueldidonna.architex.mathevaluator

import net.objecthunter.exp4j.ExpressionBuilder

interface MathEvaluator {
    fun eval(expr: String): MathResult
}

class StandardMathEvaluator : MathEvaluator {
    override fun eval(expr: String): MathResult = try {
        val result = ExpressionBuilder(expr.sanitizeForEvaluation()).build().evaluate()
        MathResult.Success(result)
    } catch (e: Exception) {
        MathResult.Error(e)
    }
}