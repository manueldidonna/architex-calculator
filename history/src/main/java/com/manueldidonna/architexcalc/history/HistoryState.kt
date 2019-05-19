package com.manueldidonna.architexcalc.history

import com.manueldidonna.arch.state.ArxState
import com.manueldidonna.architexcalc.history.data.HistoryItem

data class HistoryState(
    val data: List<HistoryItem> = listOf(),
    val askConfirmation: Boolean = true,
    val selectedItems: Int = 0
) : ArxState