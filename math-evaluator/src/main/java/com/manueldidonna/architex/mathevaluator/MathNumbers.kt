package com.manueldidonna.architex.mathevaluator

import com.manueldidonna.architex.mathevaluator.numbers.DecimalNumber
import com.manueldidonna.architex.mathevaluator.numbers.IntegerNumber
import com.manueldidonna.architex.mathevaluator.numbers.NumberElement
import com.manueldidonna.architex.mathevaluator.numbers.ScientificNotation
import com.manueldidonna.architex.mathevaluator.utils.checkDebug
import com.manueldidonna.architex.mathevaluator.utils.isMinusSymbol

fun MathExpression.number(number: Int): MathExpression = number(number.toString())
fun MathExpression.number(number: String): MathExpression = apply {
    checkDebug(debug, { !number.any { !it.isDigit() } }) { "String must contains only digits" }
    (getLastElement() as? NumberElement)
        ?.append(number)
        ?: insertElement(IntegerNumber(mathFormat).apply { append(number) })
}

fun MathExpression.decimalSeparator(): MathExpression = apply {
    val last = getLastElement()
    if (last !is DecimalNumber && last !is ScientificNotation) {
        insertElement(DecimalNumber(mathFormat))
    }
}

fun MathExpression.scientificNotation(): MathExpression = apply {
    val last = getLastElement()
    if (last !is ScientificNotation && (last is IntegerNumber || last is DecimalNumber)) {
        insertElement(ScientificNotation())
    }
}

fun MathExpression.scientificNumber(number: String): MathExpression = apply {
    checkDebug(debug, { number.count { it == 'E' } == 1 }) { "$number must contains a scientific notation 'E'" }
    if (getLastElement() is ScientificNotation) return this
    number.forEach { char ->
        when {
            char.isDigit() -> number(char.toString())
            char.isMinusSymbol() -> {
                val lastElement = getLastElement()
                if (lastElement is ScientificNotation && lastElement.isMinusOperatorAccepted)
                    lastElement.append(MINUS_SYMBOL)
            }
            char == mathFormat.decimalSeparator -> decimalSeparator()
            char == 'E' -> scientificNotation()
        }
    }
}