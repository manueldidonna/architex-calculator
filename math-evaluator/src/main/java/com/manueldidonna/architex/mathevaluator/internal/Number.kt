package com.manueldidonna.architex.mathevaluator.internal

import com.manueldidonna.architex.mathevaluator.MathFormat
import java.math.BigInteger

internal data class Number(private val mathFormat: MathFormat) :
    MathElement {
    private val activeBuilder = StringBuilder()
    private val frozenBuilder = StringBuilder()
    var isDecimal = false
        private set
    var isScientific = false
        private set

    fun append(newNumber: String) {
        if (isDecimal || isScientific) {
            frozenBuilder.append(newNumber)
        } else {
            activeBuilder.append(newNumber)
        }
    }

    fun append(newNumber: Char) {
        if (isDecimal || isScientific) {
            frozenBuilder.append(newNumber)
        } else {
            activeBuilder.append(newNumber)
        }
    }

    fun appendDecimalSeparator() {
        if (!isDecimal && !isScientific) {
            frozenBuilder.append(mathFormat.decimalSeparator)
            isDecimal = true
        }
    }

    fun appendScientificNotation() {
        if (!isScientific) {
            frozenBuilder.append('E')
            isScientific = true
        }
    }

    override val value: String
        get() = if (frozenBuilder.isNotEmpty()) {
            mathFormat.format(BigInteger(activeBuilder.toString())) + frozenBuilder.toString()
        } else mathFormat.format(BigInteger(activeBuilder.toString()))

    override fun delete(): Boolean {
        if (frozenBuilder.isNotEmpty())
            frozenBuilder.deleteCharAt(frozenBuilder.lastIndex)
        else if (activeBuilder.isNotEmpty())
            activeBuilder.deleteCharAt(activeBuilder.lastIndex)
        isDecimal = frozenBuilder.isNotEmpty() && isDecimal
        isScientific = frozenBuilder.contains('E')
        return activeBuilder.length + frozenBuilder.length == 0
    }
}