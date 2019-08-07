package com.manueldidonna.architex.mathevaluator

import com.manueldidonna.architex.mathevaluator.utils.isDivideSymbol
import com.manueldidonna.architex.mathevaluator.utils.isMinusSymbol
import com.manueldidonna.architex.mathevaluator.utils.isMultiplySimbol

/**
 * if [overwriteCurrentExpression] is true [newExpression] will overwrite
 * the current one otherwise it's appended
 */
fun MathExpression.expression(newExpression: String, overwriteCurrentExpression: Boolean) {
    if (overwriteCurrentExpression)
        clear()
    scanExpression(newExpression)
}

private fun MathExpression.scanExpression(newExpr: String) {
    var currentFuncName = ""
    var isFunc = false
    fun fixFunc() {
        if (isFunc) {
            function(SingleNameFunction(currentFuncName))
            currentFuncName = ""
            isFunc = false
        }
    }

    fun fixFuncWithOp(operator: MathOperator) {
        fixFunc()
        operator(operator)
    }
    newExpr.forEach { char ->
        when {
            char.isDigit() -> {
                fixFunc()
                number(char.toString())
            }
            char == mathFormat.decimalSeparator -> {
                fixFunc()
                decimalSeparator()
            }
            char == OPEN_BRACKET_SYMBOL_CHAR -> {
                if (isFunc) {
                    function(currentFuncName)
                    currentFuncName = ""
                    isFunc = false
                } else bracket(MathBracket.OPEN)
            }
            char == CLOSE_BRACKET_SYMBOL_CHAR -> {
                fixFunc()
                bracket(MathBracket.CLOSE)
            }
            char == 'E' -> {
                fixFunc()
                scientificNotation()
            }
            char.isLetter() -> {
                isFunc = true
                currentFuncName += char
            }
            char == PLUS_SYMBOL_CHAR -> fixFuncWithOp(MathOperator.PLUS)
            char.isMinusSymbol() -> fixFuncWithOp(MathOperator.MINUS)
            char.isDivideSymbol() -> fixFuncWithOp(MathOperator.DIVIDE)
            char.isMultiplySimbol() -> fixFuncWithOp(MathOperator.MULTIPLY)
        }
    }
}