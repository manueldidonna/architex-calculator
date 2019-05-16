package com.manueldidonna.architexcalc.history

sealed class HistoryEvent {
    object GoBack : HistoryEvent()
    object ClearHistory : HistoryEvent()
    object ClearSelectedItems : HistoryEvent()
    data class SelectItem(val itemId: Long, val isSelected: Boolean): HistoryEvent()
}