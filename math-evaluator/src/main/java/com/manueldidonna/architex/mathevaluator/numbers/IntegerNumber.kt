package com.manueldidonna.architex.mathevaluator.numbers

import com.manueldidonna.architex.mathevaluator.format.MathFormat
import java.math.BigInteger

internal data class IntegerNumber(private val mathFormat: MathFormat) :
    NumberElement {
    private val numberBuilder = StringBuilder()

    private var isCacheValid = false

    private var cachedValue: String = ""

    override val value: String
        get() {
            if (isCacheValid)
                return cachedValue
            val length = numberBuilder.length
            cachedValue = when {
                length <= 8 -> mathFormat.format(numberBuilder.toString().toInt())
                length <= 14 -> mathFormat.format(numberBuilder.toString().toLong())
                else -> mathFormat.format(BigInteger(numberBuilder.toString()))
            }
            isCacheValid = true
            return cachedValue
        }

    override fun append(newNumber: String) {
        isCacheValid = false
        if (numberBuilder.toString() == "0")
            numberBuilder.deleteCharAt(numberBuilder.lastIndex)
        numberBuilder.append(newNumber)
    }

    override fun delete(): Boolean {
        if (numberBuilder.isNotEmpty()) {
            numberBuilder.deleteCharAt(numberBuilder.lastIndex)
            isCacheValid = false
        }
        return numberBuilder.isEmpty()
    }
}