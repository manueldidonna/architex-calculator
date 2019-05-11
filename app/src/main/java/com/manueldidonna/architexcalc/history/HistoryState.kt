package com.manueldidonna.architexcalc.history

import com.manueldidonna.arch.state.ArxState
import com.manueldidonna.architexcalc.core.db.entities.HistoryEntity

data class HistoryState(
    val data: List<HistoryEntity> = listOf(),
    val askConfirmation: Boolean = true
) : ArxState