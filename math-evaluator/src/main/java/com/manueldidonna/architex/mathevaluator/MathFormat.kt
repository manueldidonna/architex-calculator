package com.manueldidonna.architex.mathevaluator

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

interface MathFormat {
    val groupingSeparator: Char
    val decimalSeparator: Char
    fun format(number: Int): String
    fun format(number: Double): String
    fun format(number: Long): String
}

class StandardMathFormat(
    locale: Locale = Locale.ITALIAN,
    configuration: MathConfiguration = MathConfiguration()
) : MathFormat {
    private val decimalFormat = NumberFormat.getInstance(locale) as DecimalFormat

    init {
        decimalFormat.maximumFractionDigits = configuration.maximumFractionDigits
    }

    override val groupingSeparator = decimalFormat.decimalFormatSymbols.groupingSeparator
    override val decimalSeparator = decimalFormat.decimalFormatSymbols.decimalSeparator
    override fun format(number: Int): String = decimalFormat.format(number)
    override fun format(number: Double): String = decimalFormat.format(number)
    override fun format(number: Long): String = decimalFormat.format(number)
}