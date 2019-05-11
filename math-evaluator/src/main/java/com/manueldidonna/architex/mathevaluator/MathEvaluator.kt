package com.manueldidonna.architex.mathevaluator

interface MathEvaluator {
    fun eval(expr: String): EvaluationResult
}