package com.manueldidonna.architexcalc.history

import com.manueldidonna.arch.state.ArxState
import com.manueldidonna.architexcalc.core.db.entities.HistoryEntity
import com.manueldidonna.architexcalc.history.data.HistoryItem

data class HistoryState(
    val data: List<HistoryItem> = listOf(),
    val askConfirmation: Boolean = true,
    val isSelectionActive: Boolean = false
) : ArxState