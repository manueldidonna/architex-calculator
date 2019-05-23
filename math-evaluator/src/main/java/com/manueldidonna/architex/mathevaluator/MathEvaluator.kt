package com.manueldidonna.architex.mathevaluator

import net.objecthunter.exp4j.ExpressionBuilder

interface MathEvaluator {
    fun eval(expr: String): MathResult
}

class StandardMathEvaluator(private val mathFormat: MathFormat) : MathEvaluator {
    override fun eval(expr: String): MathResult = try {
        MathResult.Success(
            value = ExpressionBuilder(expr.sanitizeForEvaluation()).build().evaluate()
        )
    } catch (e: Exception) {
        MathResult.Error(e)
    }

    private fun String.sanitizeForEvaluation(): String {
        return replace(mathFormat.decimalSeparator, '.')
            .replace(DIVIDE_SYMBOL, ALTERNATIVE_DIVIDE_SYMBOL)
            .replace(MULTIPLY_SYMBOL, ALTERNATIVE_MULTIPLY_SYMBOL)
            .replace(mathFormat.groupingSeparator.toString(), "")
    }

}