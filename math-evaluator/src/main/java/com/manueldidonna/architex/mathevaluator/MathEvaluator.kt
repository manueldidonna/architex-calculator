package com.manueldidonna.architex.mathevaluator

import com.manueldidonna.architex.mathevaluator.internal.*
import com.manueldidonna.architex.mathevaluator.internal.ALTERNATIVE_DIVIDE_SYMBOL_CHAR
import com.manueldidonna.architex.mathevaluator.internal.ALTERNATIVE_MULTIPLY_SYMBOL_CHAR
import com.manueldidonna.architex.mathevaluator.internal.DIVIDE_SYMBOL_CHAR
import com.manueldidonna.architex.mathevaluator.internal.MINUS_SYMBOL_CHAR
import com.manueldidonna.architex.mathevaluator.internal.MULTIPLY_SYMBOL_CHAR
import net.objecthunter.exp4j.ExpressionBuilder

interface MathEvaluator {
    fun eval(expr: String): MathResult
}

class StandardMathEvaluator(private val mathFormat: MathFormat) : MathEvaluator {
    override fun eval(expr: String): MathResult = try {
        MathResult.Success(
            value = ExpressionBuilder(expr.sanitizeForEvaluation())
                .build()
                .evaluate()
        )
    } catch (e: Exception) {
        MathResult.Error(e)
    }

    private fun String.sanitizeForEvaluation(): String {
        return replace(DIVIDE_SYMBOL_CHAR, ALTERNATIVE_DIVIDE_SYMBOL_CHAR)
            .replace(MULTIPLY_SYMBOL_CHAR, ALTERNATIVE_MULTIPLY_SYMBOL_CHAR)
            .replace(MINUS_SYMBOL_CHAR, ALTERNATIVE_MINUS_SYMBOL_CHAR)
            .replace(mathFormat.groupingSeparator.toString(), "")
            .replace(mathFormat.decimalSeparator, '.')
    }

}