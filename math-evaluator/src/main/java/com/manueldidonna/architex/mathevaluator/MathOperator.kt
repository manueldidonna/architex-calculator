package com.manueldidonna.architex.mathevaluator

import com.manueldidonna.architex.mathevaluator.numbers.ScientificNotation

internal const val PLUS_SYMBOL = "+"
internal const val PLUS_SYMBOL_CHAR = '+'
internal const val MINUS_SYMBOL = "−"
internal const val MINUS_SYMBOL_CHAR = '−'
internal const val ALTERNATIVE_MINUS_SYMBOL_CHAR = '-'
internal const val MULTIPLY_SYMBOL = "×"
internal const val MULTIPLY_SYMBOL_CHAR = '×'
internal const val ALTERNATIVE_MULTIPLY_SYMBOL_CHAR = '*'
internal const val DIVIDE_SYMBOL = "÷"
internal const val DIVIDE_SYMBOL_CHAR = '÷'
internal const val ALTERNATIVE_DIVIDE_SYMBOL_CHAR = '/'
const val EXP_SYMBOL = "^"
internal const val EXP_SYMBOL_CHAR = '^'

internal interface Operator : MathElement

internal object PlusOperator : Operator {
    override val value = PLUS_SYMBOL
}

internal object MinusOperator : Operator {
    override val value = MINUS_SYMBOL
}

internal object DivideOperator : Operator {
    override val value = DIVIDE_SYMBOL
}

internal object MultiplyOperator : Operator {
    override val value = MULTIPLY_SYMBOL
}

internal object ExpOperator : Operator {
    override val value = EXP_SYMBOL
}

enum class MathOperator(internal val value: Operator) {
    PLUS_MINUS(PlusOperator),
    PLUS(PlusOperator),
    MINUS(MinusOperator),
    MULTIPLY(MultiplyOperator),
    DIVIDE(DivideOperator),
    EXP(ExpOperator)
}

fun MathExpression.operator(op: MathOperator): MathExpression = apply {
    val size = getElementsSize()
    val lastElement = getLastElement()
    if (size <= 0 || !(size == 1 && lastElement is MinusOperator)) {
        val isPlusMinus = op == MathOperator.PLUS_MINUS
        when {
            lastElement is ScientificNotation && lastElement.isMinusOperatorAccepted
                    && (op == MathOperator.MINUS || isPlusMinus) -> lastElement.append(MINUS_SYMBOL)
            size == 0 || lastElement is HasOpenBracket -> {
                if (isPlusMinus || op == MathOperator.MINUS)
                    insertElement(MinusOperator)
            }
            isPlusMinus && lastElement is PlusOperator -> replaceLastElement(MinusOperator)
            isPlusMinus && lastElement is MinusOperator -> replaceLastElement(PlusOperator)
            lastElement is Operator -> {
                val removedOperator = removeLastElement()
                if (getLastElement() !is HasOpenBracket || op == MathOperator.PLUS || op == MathOperator.MINUS) {
                    insertElement(op.value)
                } else insertElement(removedOperator)
            }
            else -> insertElement(op.value)
        }
    }
}