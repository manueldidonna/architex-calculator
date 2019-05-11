package com.manueldidonna.architex.mathevaluator

internal const val DECIMAL_SEPARATOR_STR = ","
const val DECIMAL_SEPARATOR = ','
const val PLUS_MINUS_SYMBOL = '±'
const val DIVISION_SYMBOL = '÷'
const val MULTIPLICATION_SYMBOL = '×'
const val OPENING_BRACKET = '('
const val CLOSING_BRACKET = ')'

fun String.sanitizeForEvaluation(): String {
    // DECIMAL_SEPARATOR must not be in conflict with dot notation
    require(DECIMAL_SEPARATOR != '.')
    return removeLastMathOperator()
        .replace(".", "")
        .replace(DIVISION_SYMBOL, '/')
        .replace(MULTIPLICATION_SYMBOL, '*')
        .replace(DECIMAL_SEPARATOR, '.')
}

fun String.appendNumber(value: String): String {
    return when {
        value != DECIMAL_SEPARATOR_STR -> "$this$value"
        !substringAfterLast(" ").contains(DECIMAL_SEPARATOR) -> "$this$DECIMAL_SEPARATOR"
        else -> this
    }
}

fun String.appendOperator(value: Char): String {
    val isPlusMinus = value == PLUS_MINUS_SYMBOL
    if (isEmpty())
        return if (isPlusMinus) "- " else ""

    if (this == "- ")
        return this

    val isMathOperator = isMathOperator()
    return when {
        isPlusMinus && matchMathOperator('+') -> replaceLastMathOperator('-')
        isPlusMinus && (matchMathOperator('-') || isMathOperator) -> replaceLastMathOperator('+')
        !isPlusMinus && isMathOperator -> replaceLastMathOperator(value)
        else -> "$this ${if (isPlusMinus) '+' else value} "
    }
}

fun String.removeLastElement(): String = when {
    isEmpty() -> this
    last().isDigitOrSeparator() -> dropLast(1)
    isMathOperator() -> removeLastMathOperator()

    last() == CLOSING_BRACKET -> dropLast(1)
    last() == OPENING_BRACKET -> {
        this
    }
    else -> TODO("Remove standard/custom math functions: cos, tan, ...")
}


private fun Char.isDigitOrSeparator(): Boolean = isDigit() || this == DECIMAL_SEPARATOR

private fun String.isMathOperator(): Boolean {
    return (if (last().isWhitespace()) this[length - 2] else last()).run {
        this == MULTIPLICATION_SYMBOL || this == '+' || this == DIVISION_SYMBOL || this == '-'
    }
}


private fun String.matchMathOperator(operator: Char): Boolean {
    return (if (last().isWhitespace()) this[length - 2] else last()) == operator
}

private fun String.replaceLastMathOperator(newOperator: Char): String {
    return "${dropLast(if (last().isWhitespace()) 2 else 1)}$newOperator "
}

private fun String.removeLastMathOperator(): String {
    return if (isNotEmpty() && isMathOperator())
        dropLast(if (last().isWhitespace()) 3 else 2)
    else this
}