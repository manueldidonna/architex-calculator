package com.manueldidonna.architex.mathevaluator.internal

internal data class Function(val name: String) :
    MathElement {
    override val value = "$name("
}