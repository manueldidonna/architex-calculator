package com.manueldidonna.architex.mathevaluator

/**
 * [value] is the name of the constant. Es: 'pi', 'e', ...
 * [sanitizedValue] is the value of che constant
 */
class MathConstant(name: String, constValue: String) : MathElement {
    override val value: String = name
    override val sanitizedValue: String = constValue
}

fun MathExpression.constant(constant: MathConstant): MathExpression = apply {
    insertElement(constant)
}