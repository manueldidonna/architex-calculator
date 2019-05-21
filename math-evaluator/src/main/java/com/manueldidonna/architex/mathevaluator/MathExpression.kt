package com.manueldidonna.architex.mathevaluator

class MathExpression(
    private val mathFormat: MathFormat,
    initialExpression: String = "",
    private val debug: Boolean = false
) {
    private var frozenBuilder = StringBuilder()
    private var activeBuilder = StringBuilder()
    private var isNumber = false
    // private var openBrackets = 0

    init {
        if (initialExpression.isNotBlank()) {
            // TODO: scanExpression()
        }
    }

    fun appendNumber(number: Char) {
        checkDebug(number.isDigit()) { "Char is not a digit" }
        isNumber = true
        activeBuilder.append(number)
    }

    fun appendNumber(number: String) {
        checkDebug(!number.any { !it.isDigit() }) { "String must contains only digits" }
        isNumber = true
        activeBuilder.append(number)
    }

    fun appendOperator(operator: MathOperator) {
        if (isNumber)
            freezeBuilder()
        if (!(frozenBuilder.length == 1 && frozenBuilder.last() == '-')) {
            val isPlusMinus = operator == MathOperator.PLUS_MINUS
            when {
                frozenBuilder.isEmpty() -> if (isPlusMinus || operator == MathOperator.MINUS) {
                    frozenBuilder.append('-')
                }
                isPlusMinus && frozenBuilder.last() == '+' -> frozenBuilder.replaceLast('-')
                isPlusMinus && frozenBuilder.last() == '-' -> frozenBuilder.replaceLast('+')
                !isPlusMinus && checkLastIsOperator() -> frozenBuilder.replaceLast(operator.value)
                else -> frozenBuilder.append(if (isPlusMinus) '+' else operator.value)
            }
        }
    }

    private fun StringBuilder.replaceLast(newValue: Char) {
        deleteCharAt(lastIndex).append(newValue)
    }

    private fun checkLastIsOperator() = frozenBuilder.last().run {
        this == '-' || this == '+' || this == MathOperator.DIVIDE.value || this == MathOperator.MULTIPLY.value
    }

    private fun freezeBuilder() {
        // TODO: avoid crash when activeBuilder.toString().toLong() is greater than Long.MAX_VALUE
        frozenBuilder.append(mathFormat.format(activeBuilder.toString().toLong()))
        activeBuilder.clear()
        isNumber = false
    }

    private inline fun checkDebug(condition: Boolean, lazyMessage: () -> Any) {
        if (debug)
            require(condition, lazyMessage)
    }

}