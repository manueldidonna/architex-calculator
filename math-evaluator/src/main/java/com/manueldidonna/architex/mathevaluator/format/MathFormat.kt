package com.manueldidonna.architex.mathevaluator.format

import java.math.BigInteger

interface MathFormat {
    val groupingSeparator: Char
    val decimalSeparator: Char
    fun format(number: Int): String
    fun format(number: Double): String
    fun format(number: Long): String
    fun format(number: BigInteger): String
}