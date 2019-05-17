package com.manueldidonna.architexcalc.history.data

import com.manueldidonna.architexcalc.history.HistoryState
import com.manueldidonna.architexcalc.history.HistoryViewModel
import com.manueldidonna.architexcalc.history.data.interactors.DeleteHistoryInteractor
import com.manueldidonna.architexcalc.history.data.interactors.GetHistoryInteractor
import org.koin.dsl.module

val historyKoinModule = module(override = true) {
    factory { GetHistoryInteractor(get()) }
    factory { DeleteHistoryInteractor(get()) }
    scoped { (state: HistoryState) ->
        HistoryViewModel(
            initialState = state,
            prefsManager = get(),
            getHistory = get(),
            deleteHistory = get()
        )
    }
}