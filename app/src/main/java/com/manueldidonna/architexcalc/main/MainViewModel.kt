package com.manueldidonna.architexcalc.main

import com.manueldidonna.architex.mathevaluator.*
import com.manueldidonna.architexcalc.core.base.BaseViewModel
import com.manueldidonna.architexcalc.core.launchInteractor
import com.manueldidonna.architexcalc.data.interactors.InsertHistoryInteractor
import com.manueldidonna.architexcalc.main.numkeyboard.NumKeyboardEvent
import org.threeten.bp.LocalDate
import timber.log.Timber

class MainViewModel(
    initialState: MainState,
    private val mathFormat: MathFormat,
    private val mathEvaluator: MathEvaluator,
    private val mathExpression: MathExpression,
    private val insertHistory: InsertHistoryInteractor
) : BaseViewModel<MainState>(initialState) {

    fun dispatch(event: NumKeyboardEvent) = when (event) {
        is NumKeyboardEvent.DecimalSeparator -> setExpression { appendDecimalSeparator() }
        is NumKeyboardEvent.Number -> setExpression { appendNumber(event.value) }
        is NumKeyboardEvent.Operator -> setExpression { appendOperator(event.value) }
        is NumKeyboardEvent.Cancel -> setExpression { deleteLastElement() }
        is NumKeyboardEvent.Clear -> setExpression { clear() }
        is NumKeyboardEvent.Equal -> clearAndSaveToHistory()
    }

    private inline fun setExpression(crossinline expr: MathExpression.() -> Unit) = setState {
        val newExpression = mathExpression.apply(expr).buildExpression(sanitize = true)
        val notFormattedExpression = mathExpression.buildExpression(sanitize = false)
        return@setState if (newExpression.isEmpty()) {
            copy(expression = notFormattedExpression, result = "", errorOnResult = false)
        } else {
            when (val mathResult = mathExpression.eval(mathEvaluator)) {
                is MathResult.Success -> copy(
                    expression = notFormattedExpression,
                    result = mathResult.format(mathFormat).let { formattedResult ->
                        return@let if (formattedResult == newExpression) "" else formattedResult
                    },
                    errorOnResult = false
                )
                is MathResult.Error -> copy(errorOnResult = true)
            }
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
                date = LocalDate.now().toString(),
                expression = mathExpression.buildExpression(sanitize = true),
                result = result
            )
        )
        mathExpression.addExpression(this.result, overwrite = true)
        return@setState copy(expression = this.result, result = "", errorOnResult = false)
    }

    // TODO: remove fakeSubscribe
    fun fakeSubscribe(subscriber: (MainState) -> Unit) {
        subscribeState(subscriber = subscriber)
    }

}