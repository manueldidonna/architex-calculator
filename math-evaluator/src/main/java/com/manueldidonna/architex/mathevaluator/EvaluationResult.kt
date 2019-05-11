package com.manueldidonna.architex.mathevaluator

sealed class EvaluationResult {
    data class Success(val value: Double) : EvaluationResult() {
        fun format(formatter: Formatter): String = formatter.format(value)
    }

    data class Error(val exception: Exception) : EvaluationResult()
}