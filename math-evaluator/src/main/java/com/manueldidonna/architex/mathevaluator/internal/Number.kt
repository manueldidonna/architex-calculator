package com.manueldidonna.architex.mathevaluator.internal

import com.manueldidonna.architex.mathevaluator.MathFormat
import java.math.BigInteger

internal interface NumberElement : MathElement {
    fun append(newNumber: String)
}

internal data class DecimalNumber(private val mathFormat: MathFormat) : NumberElement {
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

internal class ScientificNotation : NumberElement {
    private var number: String = ""
    override val value: String get() = if (number.isEmpty()) "E0" else "E$number"

    val isMinusOperatorAccepted: Boolean get() = number.isEmpty()

    private var canBeDeleted = 2

    override fun append(newNumber: String) {
        canBeDeleted = 2
        number += newNumber
    }

    override fun delete(): Boolean {
        if (number.isNotEmpty()) {
            number = number.dropLast(1)
        }
        if(number.isEmpty())
            canBeDeleted--
        return canBeDeleted == 0
    }
}

internal data class IntegerNumber(private val mathFormat: MathFormat) : NumberElement {
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