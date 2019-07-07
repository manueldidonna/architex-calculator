package com.manueldidonna.architexcalc.data

import androidx.core.os.ConfigurationCompat
import com.manueldidonna.architex.mathevaluator.*
import com.manueldidonna.architexcalc.BuildConfig
import com.manueldidonna.architexcalc.data.interactors.InsertHistoryInteractor
import com.manueldidonna.architexcalc.main.MainState
import com.manueldidonna.architexcalc.main.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import java.util.*

val appKoinModule = module(override = true) {
    factory<Locale> { ConfigurationCompat.getLocales(androidApplication().resources.configuration).get(0) }
    factory<MathFormat> { StandardMathFormat(locale = get()) }
    factory { MathExpression(mathFormat = get(), debug = BuildConfig.DEBUG) }
    factory<MathEvaluator> { Exp4JEvaluator(mathFormat = get()) }
    factory { InsertHistoryInteractor(get()) }
    scoped { (state: MainState) ->
        MainViewModel(
            initialState = state,
            mathFormat = get(),
            scientificMathFormat = StandardMathFormat(
                locale = get(),
                configuration = Configuration(useScientificNotation = true, maximumIntegerDigits = 1)
            ),
            mathEvaluator = get(),
            mathExpression = get(),
            insertHistory = get()
        )
    }
}