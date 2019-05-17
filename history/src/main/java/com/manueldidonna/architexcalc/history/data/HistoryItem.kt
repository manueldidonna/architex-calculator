package com.manueldidonna.architexcalc.history.data

data class HistoryItem(
    val id: Long,
    val expression: String,
    val result: String,
    val isSelected: Boolean = false
)