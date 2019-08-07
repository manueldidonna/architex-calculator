package com.manueldidonna.architex.mathevaluator

import com.manueldidonna.architex.mathevaluator.utils.checkDebug

sealed class MathFunction : MathElement, HasOpenBracket {
    abstract val name: String
}

data class SingleNameFunction(override val name: String) : MathFunction() {
    override val value = "$name("
}

/**
 * [value] is shown in the expression
 * [alternativeValue] is the real name of the function and it depends on the [MathEvaluator] chosen
 */
class DoubleNameFunction(override val name: String, alternativeName: String) : MathFunction() {
    override val value = "$name("
    override val sanitizedValue: String = "$alternativeName("
}

fun MathExpression.function(func: String): MathExpression = apply {
    checkDebug(debug, { !func.any { !it.isLetter() || it.isUpperCase() } }) {
        "String must contains only lower case letters"
    }
    var openBrackets = config(OPEN_BRACKETS_KEY, 0)
    config[OPEN_BRACKETS_KEY] = ++openBrackets
    insertElement(SingleNameFunction(name = func))
}

fun MathExpression.function(func: MathFunction): MathExpression = apply {
    var openBrackets = config(OPEN_BRACKETS_KEY, 0)
    config[OPEN_BRACKETS_KEY] = ++openBrackets
    insertElement(func)
}