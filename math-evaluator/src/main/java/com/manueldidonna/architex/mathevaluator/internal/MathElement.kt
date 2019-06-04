package com.manueldidonna.architex.mathevaluator.internal

internal interface MathElement {
    val value: String
    fun delete(): Boolean = true
}