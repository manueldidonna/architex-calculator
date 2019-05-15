package com.manueldidonna.architexcalc.history

sealed class HistoryEvent {
    object GoBack : HistoryEvent()
    object ClearHistory : HistoryEvent()
}