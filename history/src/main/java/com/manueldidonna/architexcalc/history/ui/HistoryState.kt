package com.manueldidonna.architexcalc.history.ui

import com.manueldidonna.arch.state.ArxState
import com.manueldidonna.architexcalc.database.History

data class HistoryState(
    val data: List<History> = listOf(),
    val askToClearHistory: Boolean = true,
    val selectedItemIds: Set<Long> = hashSetOf()
) : ArxState