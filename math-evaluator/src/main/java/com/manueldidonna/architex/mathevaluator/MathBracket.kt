package com.manueldidonna.architex.mathevaluator

const val OPEN_BRACKET_SYMBOL = "("
const val OPEN_BRACKET_SYMBOL_CHAR = '('
const val CLOSE_BRACKET_SYMBOL = ")"
const val CLOSE_BRACKET_SYMBOL_CHAR = ')'

internal interface HasOpenBracket: MathElement {
    override fun onDeleteCallback(mathExpression: MathExpression) {
        mathExpression.config[OPEN_BRACKETS_KEY] = mathExpression.config<Int>(OPEN_BRACKETS_KEY) - 1
    }
}

internal interface Bracket : MathElement

internal object OpenBracket : Bracket, HasOpenBracket {
    override val value = OPEN_BRACKET_SYMBOL
}

internal object CloseBracket : Bracket {
    override val value = CLOSE_BRACKET_SYMBOL

    override fun onDeleteCallback(mathExpression: MathExpression) {
        mathExpression.config[OPEN_BRACKETS_KEY] = mathExpression.config<Int>(OPEN_BRACKETS_KEY) + 1
    }
}

enum class MathBracket(internal val value: Bracket) {
    OPEN(OpenBracket),
    CLOSE(CloseBracket)
}

internal const val OPEN_BRACKETS_KEY = "open_brackets_key"

/**
 * Cannot append a close bracket without open brackets
 * Cannot have empty brackets
 */
fun MathExpression.bracket(type: MathBracket): MathExpression = apply {
    var openBrackets = config(OPEN_BRACKETS_KEY, 0)
    if (type == MathBracket.CLOSE && getLastElement() is OpenBracket)
        return this
    if (openBrackets > 0 || type == MathBracket.OPEN) {
        if (type == MathBracket.OPEN) openBrackets++ else openBrackets--
        insertElement(type.value)
        config[OPEN_BRACKETS_KEY] = openBrackets
    }
}