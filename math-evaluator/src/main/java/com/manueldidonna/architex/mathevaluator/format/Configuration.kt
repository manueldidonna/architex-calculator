package com.manueldidonna.architex.mathevaluator.format

data class Configuration (
    val maximumFractionDigits: Int  = 4,
    val maximumIntegerDigits: Int = 40,
    val minimumFractionDigits: Int = 0,
    val minimumIntegerDigits: Int = 1,
    val useScientificNotation: Boolean = false
)