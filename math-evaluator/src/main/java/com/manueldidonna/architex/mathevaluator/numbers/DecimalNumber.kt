package com.manueldidonna.architex.mathevaluator.numbers

import com.manueldidonna.architex.mathevaluator.format.MathFormat

internal data class DecimalNumber(private val mathFormat: MathFormat) :
    NumberElement {
    private val numberBuilder = StringBuilder()
    override val value: String
        get() = mathFormat.decimalSeparator.toString() + numberBuilder.toString()

    private var canBeDeleted = 2

    override fun append(newNumber: String) {
        canBeDeleted = 2
        numberBuilder.append(newNumber)
    }

    override fun delete(): Boolean {
        if (numberBuilder.isNotEmpty()) {
            numberBuilder.deleteCharAt(numberBuilder.lastIndex)
        }
        if (numberBuilder.isEmpty()) {
            canBeDeleted--
        }
        return canBeDeleted == 0
    }
}