package com.manueldidonna.architex.mathevaluator

sealed class MathResult {
    data class Success(val value: Double) : MathResult()
    data class Error(val exception: Exception) : MathResult()
}