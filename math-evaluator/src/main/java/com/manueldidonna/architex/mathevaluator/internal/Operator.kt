package com.manueldidonna.architex.mathevaluator.internal

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


internal interface Operator : MathElement

internal class PlusOperator : Operator {
    override val value = PLUS_SYMBOL
}

internal class MinusOperator : Operator {
    override val value = MINUS_SYMBOL
}

internal class DivideOperator : Operator {
    override val value = DIVIDE_SYMBOL
}

internal class MultiplyOperator : Operator {
    override val value = MULTIPLY_SYMBOL
}