package com.manueldidonna.architex.mathevaluator

import com.manueldidonna.architex.mathevaluator.internal.HasOpenBracket
import com.manueldidonna.architex.mathevaluator.internal.MathElement

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
    val alternativeValue = "$alternativeName("
}