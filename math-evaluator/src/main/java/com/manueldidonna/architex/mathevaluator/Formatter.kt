package com.manueldidonna.architex.mathevaluator

import java.text.DecimalFormat

class Formatter {

    private val grouping = DecimalFormat("#,###.####")

    // TODO: expressionFormatter()

    // TODO: scientific notation

    fun format(value: Double): String {
        return grouping.format(value)
    }
}