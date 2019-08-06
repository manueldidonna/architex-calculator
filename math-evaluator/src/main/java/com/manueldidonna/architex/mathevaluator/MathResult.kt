package com.manueldidonna.architex.mathevaluator

import com.manueldidonna.architex.mathevaluator.format.MathFormat

sealed class MathResult {
    data class Success(val value: Double) : MathResult() {
        fun format(mathFormat: MathFormat) = mathFormat.format(value)
    }

    data class Error(val exception: Exception) : MathResult()
}