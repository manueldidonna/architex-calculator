package com.manueldidonna.architexcalc.data

import androidx.core.os.ConfigurationCompat
import com.manueldidonna.architex.mathevaluator.*
import com.manueldidonna.architexcalc.data.interactors.InsertHistoryInteractor
import com.manueldidonna.architexcalc.main.MainState
import com.manueldidonna.architexcalc.main.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appKoinModule = module(override = true) {
    factory<MathFormat> {
        StandardMathFormat(
            locale = ConfigurationCompat.getLocales(androidApplication().resources.configuration).get(0)
        )
    }
    factory { MathExpression(mathFormat = get()) }
    factory<MathEvaluator> { StandardMathEvaluator(mathFormat = get()) }
    factory { InsertHistoryInteractor(get()) }
    scoped { (state: MainState) ->
        MainViewModel(
            initialState = state,
            mathFormat = get(),
            mathEvaluator = get(),
            mathExpression = get(),
            insertHistory = get()
        )
    }
}