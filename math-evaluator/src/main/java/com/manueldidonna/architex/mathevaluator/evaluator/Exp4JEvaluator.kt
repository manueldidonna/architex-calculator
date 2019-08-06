package com.manueldidonna.architex.mathevaluator.evaluator

import com.manueldidonna.architex.mathevaluator.*
import com.manueldidonna.architex.mathevaluator.format.MathFormat
import com.manueldidonna.architex.mathevaluator.internal.ALTERNATIVE_DIVIDE_SYMBOL_CHAR
import com.manueldidonna.architex.mathevaluator.internal.ALTERNATIVE_MINUS_SYMBOL_CHAR
import com.manueldidonna.architex.mathevaluator.internal.ALTERNATIVE_MULTIPLY_SYMBOL_CHAR
import com.manueldidonna.architex.mathevaluator.internal.DIVIDE_SYMBOL_CHAR
import com.manueldidonna.architex.mathevaluator.internal.MINUS_SYMBOL_CHAR
import com.manueldidonna.architex.mathevaluator.internal.MULTIPLY_SYMBOL_CHAR
import net.objecthunter.exp4j.ExpressionBuilder

class Exp4JEvaluator(private val mathFormat: MathFormat) : MathEvaluator {
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

    companion object {
        // Constants
        val PI_CONST = MathConstant("\u03C0", "pi")
        val EULER_CONST = MathConstant("e", "e")
        // Functions
        val COS_FUNC = SingleNameFunction("cos")
        val SIN_FUNC = SingleNameFunction("sin")
        val TAN_FUNC = SingleNameFunction("tan")
        val LN_FUNC = DoubleNameFunction("ln", "log")
        val LOG10_FUNC = DoubleNameFunction("log", "log10")
        val SQRT_FUNC = DoubleNameFunction("\u221a", "sqrt")
        val EULER_EXP_FUNC = SingleNameFunction("exp")
    }
}