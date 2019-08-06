package com.manueldidonna.architex.calculator.ui.funckeyboard

import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.manueldidonna.arch.view.ArxInteractiveView
import com.manueldidonna.arch.view.ArxSubjectView
import com.manueldidonna.architex.calculator.R
import com.manueldidonna.architex.mathevaluator.*
import com.manueldidonna.architex.mathevaluator.EXP_SYMBOL
import com.manueldidonna.architexcalc.core.extensions.find

internal sealed class FuncKeyboardEvent {
    data class Function(val func: MathFunction) : FuncKeyboardEvent()
    data class Bracket(val bracket: MathBracket) : FuncKeyboardEvent()
    data class Constant(val constant: MathConstant) : FuncKeyboardEvent()
    object ExpOperator : FuncKeyboardEvent()
    object ScientificNotation : FuncKeyboardEvent()
}

internal class FuncKeyboardView(
    view: View,
    functions: List<MathFunction>,
    constants: List<MathConstant>
) : ArxInteractiveView<FuncKeyboardEvent> by ArxSubjectView() {

    init {
        view.find<EpoxyRecyclerView>(R.id.func_recycler).withModels {
            selectableHeaderEpoxyView {
                id("extra")
                headerTitle("Extra")
            }
            functions.forEach { function ->
                addMathElement(function.name) { FuncKeyboardEvent.Function(function) }
            }
            addMathElement(EXP_SYMBOL) { FuncKeyboardEvent.ExpOperator }
            addMathElement(OPEN_BRACKET_SYMBOL) { FuncKeyboardEvent.Bracket(MathBracket.OPEN) }
            addMathElement(CLOSE_BRACKET_SYMBOL) { FuncKeyboardEvent.Bracket(MathBracket.CLOSE) }
            addMathElement("E") { FuncKeyboardEvent.ScientificNotation }
            // TODO: percent operator
            // TODO: ! operator
            selectableHeaderEpoxyView {
                id("constants")
                headerTitle("Constants")
            }
            constants.forEach { const ->
                addMathElement(const.name) { FuncKeyboardEvent.Constant(const) }
            }
        }
    }

    private inline fun EpoxyController.addMathElement(name: String, crossinline event: () -> FuncKeyboardEvent) {
        mathElementEpoxyView {
            id("math.elem.$name")
            elementName(name)
            listener { _ -> emitEvent(event()) }
        }
    }

}
