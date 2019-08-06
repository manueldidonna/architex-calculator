package com.manueldidonna.architex.mathevaluator

import com.manueldidonna.architex.mathevaluator.internal.*
import com.manueldidonna.architex.mathevaluator.internal.utils.*
import java.util.*

fun MathExpression.eval(evaluator: MathEvaluator): MathResult = evaluator.eval(buildExpression(true))

inline fun MathExpression.buildExpression(sanitize: Boolean, body: MathExpression.() -> Unit): String {
    return apply(body).buildExpression(sanitize)
}

class MathExpression(
    private val mathFormat: MathFormat,
    private val debug: Boolean = false
) {
    private var openBrackets = 0
    private val elements = ArrayDeque<MathElement>()
    private val builder = StringBuilder()

    /**
     * if [overwriteCurrentExpression] is true [newExpression] will overwrite
     * the current one otherwise it's appended
     */
    fun addExpression(newExpression: String, overwriteCurrentExpression: Boolean) {
        if (overwriteCurrentExpression)
            clear()
        scanExpression(newExpression)
    }

    /**
     * Only used to scan functions within new expressions
     */
    private var isFunc = false
    private var currentFuncName = ""
    /**
     * Fix function without an open bracket
     */
    private inline fun fixFunc(body: () -> Unit) {
        if (isFunc) {
            appendFunction(currentFuncName)
            currentFuncName = ""
            isFunc = false
        }
        body()
    }

    private fun scanExpression(newExpr: String) {
        currentFuncName = ""
        isFunc = false
        newExpr.forEach { char ->
            when {
                char.isDigit() -> fixFunc { appendNumber(char.toString()) }
                char == mathFormat.decimalSeparator -> fixFunc {
                    appendDecimalSeparator()
                }
                char == OPEN_BRACKET_SYMBOL_CHAR -> {
                    if (isFunc) {
                        appendFunction(currentFuncName)
                        currentFuncName = ""
                        isFunc = false
                    } else appendBracket(MathBracket.OPEN)
                }
                char == CLOSE_BRACKET_SYMBOL_CHAR -> fixFunc { appendBracket(MathBracket.CLOSE) }
                char == 'E' -> fixFunc { appendScientificNotation() }
                char.isLetter() -> {
                    isFunc = true
                    currentFuncName += char
                }
                char == PLUS_SYMBOL_CHAR -> fixFunc { appendOperator(MathOperator.PLUS) }
                char.isMinusSymbol() -> fixFunc { appendOperator(MathOperator.MINUS) }
                char.isDivideSymbol() -> fixFunc { appendOperator(MathOperator.DIVIDE) }
                char.isMultiplySimbol() -> fixFunc { appendOperator(MathOperator.MULTIPLY) }
            }
        }
    }

    fun buildExpression(sanitizeToEval: Boolean): String {
        if (elements.size <= 0 || elements.last is HasOpenBracket)
            return ""
        builder.clear()
        if (sanitizeToEval) {
            elements.forEach { e ->
                builder.append(if (e is DoubleNameFunction) e.alternativeValue else e.value)
            }
            if (elements.last is Operator)
                builder.deleteCharAt(builder.lastIndex)
            for (i in 0 until openBrackets)
                builder.append(CLOSE_BRACKET_SYMBOL_CHAR)
        } else {
            elements.forEach { e ->
                builder.append(if (e is MathConstant) e.name else e.value)
            }
        }
        return builder.toString()
    }

    fun appendNumber(number: String) {
        checkDebug(debug, { !number.any { !it.isDigit() } }) { "String must contains only digits" }
        (elements.safeLast() as? NumberElement)
            ?.append(number)
            ?: elements.add(IntegerNumber(mathFormat).apply { append(number) })
    }

    fun appendDecimalSeparator() {
        val last = elements.safeLast()
        if (last !is DecimalNumber && last !is ScientificNotation) {
            elements.add(DecimalNumber(mathFormat))
        }
    }

    fun appendScientificNotation() {
        val last = elements.safeLast()
        if (last !is ScientificNotation && (last is IntegerNumber || last is DecimalNumber)) {
            elements.add(ScientificNotation())
        }
    }

    fun appendScientificNumber(number: String) {
        checkDebug(debug, { number.count { it == 'E' } == 1 }) { "$number must contains a scientific notation 'E'" }
        if (elements.safeLast() is ScientificNotation) return
        number.forEach { char ->
            when {
                char.isDigit() -> appendNumber(char.toString())
                char.isMinusSymbol() -> {
                    val lastElement = elements.safeLast()
                    if (lastElement is ScientificNotation && lastElement.isMinusOperatorAccepted)
                        lastElement.append(MINUS_SYMBOL)
                }
                char == mathFormat.decimalSeparator -> appendDecimalSeparator()
                char == 'E' -> appendScientificNotation()
            }
        }
    }

    fun appendOperator(operator: MathOperator) {
        if (elements.isEmpty() || !(elements.size == 1 && elements.last is MinusOperator)) {
            val isPlusMinus = operator == MathOperator.PLUS_MINUS
            val lastElement = elements.safeLast()
            when {
                lastElement is ScientificNotation && lastElement.isMinusOperatorAccepted
                        && (operator == MathOperator.MINUS || isPlusMinus) -> lastElement.append(MINUS_SYMBOL)
                elements.isEmpty() || lastElement is HasOpenBracket -> {
                    if (isPlusMinus || operator == MathOperator.MINUS)
                        elements.add(MinusOperator())
                }
                isPlusMinus && lastElement is PlusOperator -> elements.replaceLast(MinusOperator())
                isPlusMinus && lastElement is MinusOperator -> elements.replaceLast(PlusOperator())
                lastElement is Operator -> elements.apply {
                    val removedOperator = removeLast()
                    if (last !is HasOpenBracket || operator == MathOperator.PLUS || operator == MathOperator.MINUS) {
                        add(operator.value())
                    } else add(removedOperator)
                }
                else -> elements.add(operator.value())
            }
        }
    }

    fun appendFunction(func: String) {
        checkDebug(debug, { !func.any { !it.isLetter() || it.isUpperCase() } }) {
            "String must contains only lower case letters"
        }
        openBrackets++
        elements.add(SingleNameFunction(name = func))
    }

    fun appendFunction(func: MathFunction) {
        openBrackets++
        elements.add(func)
    }

    fun appendConstant(constant: MathConstant) {
        elements.add(constant)
    }

    /**
     * Cannot append a close bracket without open brackets
     * Cannot have empty brackets
     */
    fun appendBracket(type: MathBracket) {
        if (type == MathBracket.CLOSE && elements.last is OpenBracket)
            return
        if (openBrackets > 0 || type == MathBracket.OPEN) {
            if (type == MathBracket.OPEN) openBrackets++ else openBrackets--
            elements.add(type.value())
        }
    }

    fun deleteLastElement() {
        if (elements.size > 0 && elements.last.delete()) {
            when (elements.removeLast()) {
                is HasOpenBracket -> openBrackets--
                is CloseBracket -> openBrackets++
            }
        }
    }

    /**
     * Reset MathExpression to the initialization state
     */
    fun clear() {
        if (builder.isNotEmpty())
            builder.clear()
        elements.clear()
        openBrackets = 0
    }
}