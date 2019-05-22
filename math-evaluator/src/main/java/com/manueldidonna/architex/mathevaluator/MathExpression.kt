package com.manueldidonna.architex.mathevaluator

class MathExpression(
    private val mathFormat: MathFormat,
    initialExpression: String = "",
    private val debug: Boolean = false
) {
    private var frozenBuilder = StringBuilder()
    private var activeBuilder = StringBuilder()
    private var isNumber = false
    private var isDecimalSep = false
    private var openBrackets = 0

    init {
        if (initialExpression.isNotBlank()) {
            // TODO: scanExpression()
        }
    }

    fun appendNumber(number: String) {
        checkDebug(debug, !number.any { !it.isDigit() }) { "String must contains only digits" }
        if (isDecimalSep) {
            frozenBuilder.append(number)
        } else {
            isNumber = true
            activeBuilder.append(number)
        }
    }

    fun appendDecimalSeparator() {
        if (!isDecimalSep) {
            if (isNumber)
                freezeBuilder()
            isDecimalSep = true
            frozenBuilder.append(mathFormat.decimalSeparator)
        }
    }

    fun appendOperator(operator: MathOperator) = resetNumberAndDecimal {
        if (!(frozenBuilder.length == 1 && frozenBuilder.last() == MINUS_SYMBOL)) {
            val isPlusMinus = operator == MathOperator.PLUS_MINUS
            when {
                frozenBuilder.isEmpty() -> if (isPlusMinus || operator == MathOperator.MINUS) {
                    frozenBuilder.append(MINUS_SYMBOL)
                }
                isPlusMinus && frozenBuilder.last() == PLUS_SYMBOL -> frozenBuilder.replaceLast(MINUS_SYMBOL)
                isPlusMinus && frozenBuilder.last() == MINUS_SYMBOL -> frozenBuilder.replaceLast(PLUS_SYMBOL)
                !isPlusMinus && frozenBuilder.checkLastIsOperator() -> frozenBuilder.replaceLast(operator.value)
                else -> frozenBuilder.append(if (isPlusMinus) PLUS_SYMBOL else operator.value)
            }
        }
    }

    fun appendBracket(type: MathBracket) = resetNumberAndDecimal {
        if (type == MathBracket.OPEN) openBrackets++ else openBrackets--
        frozenBuilder.append(type.value)
    }

    private fun freezeBuilder() {
        // TODO: avoid crash when activeBuilder.toString().toLong() is greater than Long.MAX_VALUE
        frozenBuilder.append(mathFormat.format(activeBuilder.toString().toLong()))
        activeBuilder.clear()
        isNumber = false
    }

    private inline fun resetNumberAndDecimal(block: () -> Unit) {
        isDecimalSep = false
        if (isNumber) freezeBuilder()
        block()
    }
}