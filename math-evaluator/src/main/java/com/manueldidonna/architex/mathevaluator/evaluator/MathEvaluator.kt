package com.manueldidonna.architex.mathevaluator.evaluator

import com.manueldidonna.architex.mathevaluator.MathResult

interface MathEvaluator {
    fun eval(expr: String): MathResult
}
