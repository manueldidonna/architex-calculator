package com.manueldidonna.architexcalc.data

import com.manueldidonna.architex.mathevaluator.MathEvaluator
import com.manueldidonna.architex.mathevaluator.StandardMathEvaluator
import com.manueldidonna.architexcalc.data.interactors.InsertHistoryInteractor
import com.manueldidonna.architexcalc.main.MainState
import com.manueldidonna.architexcalc.main.MainViewModel
import org.koin.dsl.module

val appKoinModule = module(override = true) {
    factory<MathEvaluator> { StandardMathEvaluator() }
    factory { InsertHistoryInteractor(get()) }
    scoped { (state: MainState) ->
        MainViewModel(
            initialState = state,
            mathEvaluator = get(),
            insertHistory = get()
        )
    }
}