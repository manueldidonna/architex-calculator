package com.manueldidonna.architex.mathevaluator

sealed class MathResult {
    data class Success(val value: Double) : MathResult() {
        fun format(mathFormat: MathFormat) = mathFormat.format(value)
    }

    data class Error(val exception: Exception) : MathResult()
}