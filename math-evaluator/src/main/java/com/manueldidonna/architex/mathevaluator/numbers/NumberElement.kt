package com.manueldidonna.architex.mathevaluator.numbers

import com.manueldidonna.architex.mathevaluator.MathElement

internal interface NumberElement : MathElement {
    fun append(newNumber: String)
}