package com.manueldidonna.architex.mathevaluator.internal

import com.manueldidonna.architex.mathevaluator.MathFormat
import java.math.BigInteger

internal data class Number(private val mathFormat: MathFormat) : MathElement {
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
            // there isn't a check for string with multiple zeros. ex: "000" will be accepted
            if (newNumber != "0" || activeBuilder.toString() != "0") {
                if (newNumber != "0" && activeBuilder.toString() == "0")
                    activeBuilder.deleteCharAt(activeBuilder.lastIndex)
                activeBuilder.append(newNumber)
            }
        }
    }

    fun append(newNumber: Char) {
        if (isDecimal || isScientific) {
            frozenBuilder.append(newNumber)
        } else {
            if (newNumber != '0' || activeBuilder.toString() != "0") {
                if (newNumber != '0' && activeBuilder.toString() == "0")
                    activeBuilder.deleteCharAt(activeBuilder.lastIndex)
                activeBuilder.append(newNumber)
            }
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
        get() = when {
            frozenBuilder.isNotEmpty() && activeBuilder.isNotEmpty() -> {
                mathFormat.format(BigInteger(activeBuilder.toString())) + frozenBuilder.toString()
            }
            frozenBuilder.isNotEmpty() -> frozenBuilder.toString()
            activeBuilder.isNotEmpty() -> mathFormat.format(BigInteger(activeBuilder.toString()))
            else -> ""
        }

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