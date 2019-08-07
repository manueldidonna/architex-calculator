package com.manueldidonna.architex.mathevaluator.numbers

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