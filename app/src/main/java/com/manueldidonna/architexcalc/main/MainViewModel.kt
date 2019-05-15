package com.manueldidonna.architexcalc.main

import com.manueldidonna.arch.viewmodel.ArxStateViewModel
import com.manueldidonna.architex.mathevaluator.*
import com.manueldidonna.architexcalc.core.launchInteractor
import com.manueldidonna.architexcalc.data.interactors.InsertHistoryInteractor
import com.manueldidonna.architexcalc.main.numkeyboard.NumKeyboardEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import org.threeten.bp.LocalDate
import timber.log.Timber

class MainViewModel(
    initialState: MainState,
    private val mathEvaluator: MathEvaluator,
    private val insertHistory: InsertHistoryInteractor
) : ArxStateViewModel<MainState>(initialState) {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val resultFormatter = Formatter()

    fun dispatch(event: NumKeyboardEvent) = when (event) {
        is NumKeyboardEvent.Number -> setExpression { appendNumber(event.value) }
        is NumKeyboardEvent.Operator -> setExpression { appendOperator(event.value) }
        is NumKeyboardEvent.Cancel -> setExpression { removeLastElement() }
        is NumKeyboardEvent.Clear -> setExpression { "" }
        is NumKeyboardEvent.Equal -> clearAndSaveToHistory()
    }

    private inline fun setExpression(crossinline expr: String.() -> String) {
        setState { copy(expression = expression.expr()) }
        updateResult()
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
                expression = expression,
                result = result
            )
        )
        return@setState copy(expression = this.result, result = "", errorOnResult = false)
    }

    private fun updateResult() = setState {
        return@setState if (expression.isEmpty()) {
            copy(result = "", errorOnResult = false)
        } else {
            when (val mathResult = mathEvaluator.eval(expression)) {
                is EvaluationResult.Success -> copy(
                    result = mathResult.format(resultFormatter),
                    errorOnResult = false
                )
                is EvaluationResult.Error -> copy(errorOnResult = true)
            }
        }
    }

    // TODO: remove fakeSubscribe
    fun fakeSubscribe(subscriber: (MainState) -> Unit) {
        subscribeState(subscriber = subscriber)
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }

}