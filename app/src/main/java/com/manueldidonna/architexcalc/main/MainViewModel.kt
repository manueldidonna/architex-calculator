package com.manueldidonna.architexcalc.main

import com.manueldidonna.architex.mathevaluator.MathEvaluator
import com.manueldidonna.architex.mathevaluator.MathExpression
import com.manueldidonna.architex.mathevaluator.MathFormat
import com.manueldidonna.architex.mathevaluator.MathResult
import com.manueldidonna.architexcalc.core.base.BaseViewModel
import com.manueldidonna.architexcalc.core.launchInteractor
import com.manueldidonna.architexcalc.data.interactors.InsertHistoryInteractor
import com.manueldidonna.architexcalc.main.numkeyboard.NumKeyboardEvent
import timber.log.Timber

class MainViewModel(
    initialState: MainState,
    private val mathFormat: MathFormat,
    private val scientificMathFormat: MathFormat,
    private val mathEvaluator: MathEvaluator,
    private val mathExpression: MathExpression,
    private val insertHistory: InsertHistoryInteractor
) : BaseViewModel<MainState>(initialState) {

    fun appendExpression(newExpression: String, overwriteCurrentExpression: Boolean) {
        setExpression { addExpression(newExpression, overwriteCurrentExpression) }
    }

    fun dispatch(event: NumKeyboardEvent) = when (event) {
        is NumKeyboardEvent.DecimalSeparator -> setExpression { appendDecimalSeparator() }
        is NumKeyboardEvent.Number -> setExpression { appendNumber(event.value) }
        is NumKeyboardEvent.Operator -> setExpression { appendOperator(event.value) }
        is NumKeyboardEvent.Cancel -> setExpression { deleteLastElement() }
        is NumKeyboardEvent.Clear -> setExpression { clear() }
        is NumKeyboardEvent.Equal -> clearAndSaveToHistory()
    }

    private inline fun setExpression(crossinline expr: MathExpression.() -> Unit) = setState {
        val formattedExpression = mathExpression.apply(expr).buildExpression(sanitize = true)
        val unformattedExpression = mathExpression.buildExpression(sanitize = false)
        if (formattedExpression.isEmpty())
            return@setState copy(expression = unformattedExpression, result = "", errorOnResult = false)

        return@setState when (val mathResult = mathEvaluator.eval(formattedExpression)) {
            is MathResult.Success -> {
                val resultMathFormat = if (mathResult.value > 999999999) scientificMathFormat else mathFormat
                copy(
                    expression = unformattedExpression,
                    result = mathResult.format(resultMathFormat),
                    errorOnResult = false
                )
            }
            is MathResult.Error -> copy(expression = unformattedExpression, errorOnResult = true)
        }
    }

    private fun clearAndSaveToHistory() = setState {
        if (errorOnResult)
            return@setState copy(expression = "", result = "", errorOnResult = false)
        if (expression == result || result.isEmpty())
            return@setState this
        Timber.d("Saving to history")
        coroutineScope.launchInteractor(
            interactor = insertHistory,
            param = InsertHistoryInteractor.Params(
                expression = mathExpression.buildExpression(sanitize = true),
                result = result
            )
        )
        mathExpression.addExpression(this.result, overwriteCurrentExpression = true)
        return@setState copy(expression = this.result, result = "", errorOnResult = false)
    }

    // TODO: remove fakeSubscribe
    fun fakeSubscribe(subscriber: (MainState) -> Unit) {
        subscribeState(subscriber = subscriber)
    }

}