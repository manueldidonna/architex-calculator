package com.manueldidonna.architex.calculator.ui

import com.manueldidonna.architex.calculator.domain.InsertHistoryItemInteractor
import com.manueldidonna.architex.mathevaluator.*
import com.manueldidonna.architexcalc.core.base.BaseStateManager
import com.manueldidonna.architexcalc.core.launchInteractor
import timber.log.Timber

class CalculatorStateManager(
    initialState: CalculatorState,
    private val mathFormat: MathFormat,
    private val scientificMathFormat: MathFormat,
    private val mathEvaluator: MathEvaluator,
    private val mathExpression: MathExpression,
    private val insertHistoryItem: InsertHistoryItemInteractor
) : BaseStateManager<CalculatorState>(initialState) {

    fun appendNumber(value: String) = setExpression { number(value) }
    fun appendDecimalSeparator() = setExpression { decimalSeparator() }
    fun appendScientificNotation() = setExpression { scientificNotation() }
    fun appendOperator(op: MathOperator) = setExpression { operator(op) }
    fun appendBracket(bracket: MathBracket) = setExpression { bracket(bracket) }
    fun appendConstant(const: MathConstant) = setExpression { constant(const) }
    fun appendFunction(func: MathFunction) = setExpression { function(func) }
    fun clearExpression() = setExpression { clear() }
    fun deleteLastElement() = setExpression { deleteLastElement() }
    fun appendExpression(newExpression: String, overwriteCurrentExpression: Boolean) {
        setExpression { addExpression(newExpression, overwriteCurrentExpression) }
    }

    private inline fun setExpression(crossinline expr: MathExpression.() -> Unit) = setState {
        val sanitizedExpression = mathExpression.apply(expr).buildExpression(sanitizeToEval = true)
        val unSanitizedExpression = mathExpression.buildExpression(sanitizeToEval = false)
        if (sanitizedExpression.isEmpty() || sanitizedExpression == mathFormat.decimalSeparator.toString())
            return@setState copy(expression = unSanitizedExpression, result = "", errorOnResult = false)

        return@setState when (val mathResult = mathEvaluator.eval(sanitizedExpression)) {
            is MathResult.Success -> copy(
                expression = unSanitizedExpression,
                result = mathResult.fixScientNotation(),
                errorOnResult = false
            )
            is MathResult.Error -> copy(expression = unSanitizedExpression, errorOnResult = true)
        }
    }

    private fun MathResult.Success.fixScientNotation(): String {
        val resultMathFormat = if (value > 999999999 || value < -9999999) scientificMathFormat else mathFormat
        return format(resultMathFormat)
    }

    fun clearResultAndSaveToHistory() = setState {
        if (errorOnResult)
            return@setState copy(expression = "", result = "", errorOnResult = false)
        if (expression == result || result.isEmpty())
            return@setState this
        Timber.d("Saving to history")
        stateManagerScope.launchInteractor(
            interactor = insertHistoryItem,
            param = InsertHistoryItemInteractor.Params(
                expression = mathExpression.buildExpression(sanitizeToEval = false),
                result = result
            )
        )
        mathExpression.addExpression(this.result, overwriteCurrentExpression = true)
        return@setState copy(expression = this.result, result = "", errorOnResult = false)
    }
}