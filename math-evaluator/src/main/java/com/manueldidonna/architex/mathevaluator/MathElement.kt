package com.manueldidonna.architex.mathevaluator

interface MathElement {
    val value: String
    val sanitizedValue: String get() = value
    fun delete(): Boolean = true
    fun onDeleteCallback(mathExpression: MathExpression): Unit = Unit
}