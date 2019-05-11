package com.manueldidonna.architex.mathevaluator

private const val MATH_DECIMAL_SEPARATOR = ','

class MathExpression(private var expression: String = "") {

    fun appendNumber(value: String): String {
        expression = when {
            value != DECIMAL_SEPARATOR_STR -> "$expression$value"
            !expression.substringAfterLast(" ")
                .contains(MATH_DECIMAL_SEPARATOR) -> "$this$MATH_DECIMAL_SEPARATOR"
            else -> expression
        }

        return expression
    }
}