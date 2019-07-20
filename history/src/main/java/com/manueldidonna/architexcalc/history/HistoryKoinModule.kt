package com.manueldidonna.architexcalc.history

import com.manueldidonna.architexcalc.history.ui.HistoryState
import com.manueldidonna.architexcalc.history.ui.HistoryStateManager
import com.manueldidonna.architexcalc.history.domain.DeleteAllHistoryItemsInteractor
import com.manueldidonna.architexcalc.history.domain.DeleteHistoryItemsInteractor
import com.manueldidonna.architexcalc.history.domain.GetHistoryItemsInteractor
import org.koin.dsl.module

val historyKoinModule = module(override = true) {
    factory { GetHistoryItemsInteractor(get()) }
    factory { DeleteAllHistoryItemsInteractor(get()) }
    factory { DeleteHistoryItemsInteractor(get()) }
    scoped {
        HistoryStateManager(
            initialState = HistoryState(),
            prefsManager = get(),
            getHistoryItems = get(),
            deleteAllHistoryItems = get(),
            deleteHistoryItems = get()
        )
    }
}