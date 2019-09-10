package com.manueldidonna.architex.calculator.ui.funckeyboard

import android.view.View
import com.airbnb.epoxy.AsyncEpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.manueldidonna.arch.view.ArxInteractiveView
import com.manueldidonna.arch.view.ArxRenderView
import com.manueldidonna.arch.view.ArxSubjectView
import com.manueldidonna.architex.calculator.R
import com.manueldidonna.architex.calculator.ui.CalculatorStateManager
import com.manueldidonna.architex.mathevaluator.*
import com.manueldidonna.architexcalc.common.ui.extensions.find
import com.manueldidonna.architexcalc.database.Constant

internal object CreateConstantEvent

internal class FuncKeyboardView(
    view: View,
    calcStateManager: CalculatorStateManager,
    constStateManager: FuncKeyboardStateManager,
    functions: List<MathFunction>
) : ArxInteractiveView<CreateConstantEvent> by ArxSubjectView(), ArxRenderView<FuncKeyboardState> {

    private val recyclerController = RecyclerController(calcStateManager, functions,
        listener = object : RecyclerController.Listener {
            override fun onCreateConstantClick() {
                emitEvent(CreateConstantEvent)
            }

            override fun onRemoveAllConstantsClick() {
                // TODO: delete constants by name
                constStateManager.deleteAllConstants()
            }
        }
    )

    init {
        view.find<EpoxyRecyclerView>(R.id.func_recycler) {
            setControllerAndBuildModels(recyclerController)
        }
    }

    override fun render(state: FuncKeyboardState) {
        recyclerController.setData(state.savedConstants)
    }

    private class RecyclerController(
        private val stateManager: CalculatorStateManager,
        private val functions: List<MathFunction>,
        private val listener: Listener
    ) : AsyncEpoxyController() {

        interface Listener {
            fun onCreateConstantClick()
            fun onRemoveAllConstantsClick()
        }

        private var savedConstants: List<Constant> = listOf()
        fun setData(newConstants: List<Constant>) {
            this.savedConstants = newConstants
            requestModelBuild()
        }

        override fun buildModels() {
            extraHeaderEpoxyView {
                id("extra")
            }
            functions.forEach { function ->
                addMathElement(function.name) { stateManager.appendFunction(function) }
            }
            addMathElement(EXP_SYMBOL) { stateManager.appendOperator(MathOperator.EXP) }
            addMathElement(OPEN_BRACKET_SYMBOL) { stateManager.appendBracket(MathBracket.OPEN) }
            addMathElement(CLOSE_BRACKET_SYMBOL) { stateManager.appendBracket(MathBracket.CLOSE) }
            addMathElement("E") { stateManager.appendScientificNotation() }
            // TODO: percent operator
            // TODO: ! operator
            if (savedConstants.isNotEmpty()) {
                constantsHeaderEpoxyView {
                    id("constants")
                    addConstantListener { _ ->
                        listener.onCreateConstantClick()
                    }
                    removeAllConstantsListener { _ ->
                        listener.onRemoveAllConstantsClick()
                    }
                }
                savedConstants.forEach { const ->
                    addMathElement(const.name) { stateManager.appendConstant(const.name, const.value) }
                }
            }
        }

        private inline fun addMathElement(name: String, crossinline doOnClick: () -> Unit) {
            mathElementEpoxyView {
                id("math.elem.$name")
                elementName(name)
                listener { _ -> doOnClick() }
            }
        }
    }
}
