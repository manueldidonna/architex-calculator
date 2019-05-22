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

    fun appendFunction(func: String) = resetNumberAndDecimal {
        checkDebug(debug, !func.any { !it.isLetter() }) { "String must contains only letters" }
        openBrackets++
        frozenBuilder.append(func).append(OPEN_BRACKET_SYMBOL)
    }

    fun appendBracket(type: MathBracket) = resetNumberAndDecimal {
        if (type == MathBracket.OPEN) openBrackets++ else openBrackets--
        frozenBuilder.append(type.value)
    }

    fun deleteLastElement() {
        if (isNumber) {
            activeBuilder.deleteCharAt(activeBuilder.lastIndex)
            if (activeBuilder.isEmpty())
                isNumber = false
            return
        }
        if (frozenBuilder.isNotEmpty()) {
            val current = frozenBuilder[frozenBuilder.lastIndex]
            frozenBuilder.deleteCharAt(frozenBuilder.lastIndex)
            when {
                current.isDigit() -> deleteNumber()
                current == OPEN_BRACKET_SYMBOL -> {
                    openBrackets--
                    deleteFunction()
                }
                current == CLOSE_BRACKET_SYMBOL -> openBrackets++
                current == mathFormat.decimalSeparator -> isDecimalSep = false
            }
        }
    }

    private fun deleteNumber() {
        checkDebug(debug, activeBuilder.isEmpty()) { "ActiveBuilder must be empty" }
        val index = frozenBuilder.lastIndex
        loop@ for (i in index downTo 0) {
            val current = frozenBuilder[i]
            when {
                current == mathFormat.groupingSeparator -> frozenBuilder.deleteCharAt(i)
                current.isDigit() -> {
                    frozenBuilder.deleteCharAt(i)
                    activeBuilder.append(current)
                }
                else -> break@loop
            }
        }
        if (activeBuilder.isNotEmpty()) {
            isNumber = true
            activeBuilder.reverse()
        }
    }

    private fun deleteFunction() {
        val index = frozenBuilder.lastIndex
        loop@ for (i in index downTo 0) {
            if (!frozenBuilder[i].isLetter())
                break@loop
            frozenBuilder.deleteCharAt(i)
        }
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