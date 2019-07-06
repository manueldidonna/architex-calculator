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
        val sanitizedExpression = mathExpression.apply(expr).buildExpression(sanitize = true)
        val unSanitizedExpression = mathExpression.buildExpression(sanitize = false)
        if (sanitizedExpression.isEmpty() && sanitizedExpression == mathFormat.decimalSeparator.toString())
            return@setState copy(expression = unSanitizedExpression, result = "", errorOnResult = false)

        return@setState when (val mathResult = mathEvaluator.eval(sanitizedExpression)) {
            is MathResult.Success -> copy(
                expression = unSanitizedExpression,
                result = mathResult.fixFormat(),
                errorOnResult = false
            )
            is MathResult.Error -> copy(expression = unSanitizedExpression, errorOnResult = true)
        }
    }

    private fun MathResult.Success.fixFormat(): String {
        val resultMathFormat = if (value > 999999999 && value < -9999999) scientificMathFormat else mathFormat
        return format(resultMathFormat)
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