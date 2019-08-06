package com.manueldidonna.architex.calculator

import androidx.core.os.ConfigurationCompat
import com.manueldidonna.architex.calculator.domain.InsertHistoryItemInteractor
import com.manueldidonna.architex.calculator.ui.CalculatorState
import com.manueldidonna.architex.calculator.ui.CalculatorStateManager
import com.manueldidonna.architex.mathevaluator.*
import com.manueldidonna.architex.mathevaluator.evaluator.Exp4JEvaluator
import com.manueldidonna.architex.mathevaluator.evaluator.MathEvaluator
import com.manueldidonna.architex.mathevaluator.format.FormatConfiguration
import com.manueldidonna.architex.mathevaluator.format.MathFormat
import com.manueldidonna.architex.mathevaluator.format.StandardMathFormat
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import java.util.*

val calculatorKoinModule = module(override = true) {
    factory<Locale> { ConfigurationCompat.getLocales(androidApplication().resources.configuration).get(0) }
    factory<MathFormat> {
        StandardMathFormat(
            locale = get()
        )
    }
    factory { MathExpression(mathFormat = get(), debug = BuildConfig.DEBUG) }
    factory<MathEvaluator> {
        Exp4JEvaluator(
            mathFormat = get()
        )
    }
    factory { InsertHistoryItemInteractor(get()) }
    scoped { (state: CalculatorState) ->
        CalculatorStateManager(
            initialState = state,
            mathFormat = get(),
            scientificMathFormat = StandardMathFormat(
                locale = get(),
                configuration = FormatConfiguration(
                    useScientificNotation = true,
                    maximumIntegerDigits = 1
                )
            ),
            mathEvaluator = get(),
            mathExpression = get(),
            insertHistoryItem = get()
        )
    }
}