package com.manueldidonna.architex.mathevaluator.format

import java.math.BigInteger
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class StandardMathFormat(
    locale: Locale = Locale.ITALIAN,
    configuration: Configuration = Configuration()
) : MathFormat {
    private val decimalFormat = NumberFormat.getInstance(locale) as DecimalFormat

    init {
        decimalFormat.apply {
            if (configuration.useScientificNotation)
                applyPattern("0.0E0")
            minimumFractionDigits = configuration.minimumFractionDigits
            maximumFractionDigits = configuration.maximumFractionDigits
            minimumIntegerDigits = configuration.minimumIntegerDigits
            maximumIntegerDigits = configuration.maximumIntegerDigits
        }
    }

    override val groupingSeparator = decimalFormat.decimalFormatSymbols.groupingSeparator
    override val decimalSeparator = decimalFormat.decimalFormatSymbols.decimalSeparator
    override fun format(number: Int): String = decimalFormat.format(number)
    override fun format(number: Double): String = decimalFormat.format(number)
    override fun format(number: Long): String = decimalFormat.format(number)
    override fun format(number: BigInteger): String = decimalFormat.format(number)
}