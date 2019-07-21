package com.manueldidonna.architexcalc.history.ui

import com.manueldidonna.arch.state.ArxState
import com.manueldidonna.architexcalc.core.db.entities.HistoryEntity

data class HistoryState(
    val data: List<HistoryEntity> = listOf(),
    val askToClearHistory: Boolean = true,
    val selectedItemIds: Set<Long> = hashSetOf()
) : ArxState