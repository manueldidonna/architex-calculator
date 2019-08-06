package com.manueldidonna.architex.mathevaluator

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