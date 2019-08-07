package com.manueldidonna.architex.mathevaluator.numbers

import com.manueldidonna.architex.mathevaluator.format.MathFormat

internal data class DecimalNumber(private val mathFormat: MathFormat) : NumberElement {
    private val numberBuilder = StringBuilder()
    override val value: String
        get() = mathFormat.decimalSeparator.toString() + numberBuilder.toString()

    override fun append(newNumber: String) {
        numberBuilder.append(newNumber)
    }

    private var canBeDeleted = true

    override fun delete(): Boolean {
        if (numberBuilder.isNotEmpty()) {
            canBeDeleted = false
            numberBuilder.deleteCharAt(numberBuilder.lastIndex)
        }
        if (!canBeDeleted && numberBuilder.isEmpty()) {
            canBeDeleted = true
            return false
        }
        return canBeDeleted
    }
}