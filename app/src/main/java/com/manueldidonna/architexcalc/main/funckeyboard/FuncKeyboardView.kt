package com.manueldidonna.architexcalc.main.funckeyboard

import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.manueldidonna.arch.view.ArxInteractiveView
import com.manueldidonna.arch.view.ArxSubjectView
import com.manueldidonna.architex.mathevaluator.*
import com.manueldidonna.architex.mathevaluator.internal.EXP_SYMBOL
import com.manueldidonna.architexcalc.R
import com.manueldidonna.architexcalc.core.base.BaseContainerView

class FuncKeyboardView(
    view: View,
    functions: List<MathFunction>,
    constants: List<MathConstant>
) : BaseContainerView(view), ArxInteractiveView<FuncKeyboardEvent> by ArxSubjectView() {

    init {
        view.findViewById<EpoxyRecyclerView>(R.id.func_recycler).withModels {
            selectableHeaderEpoxyView {
                id("extra")
                headerTitle("Extra")
            }
            functions.forEach { function ->
                addMathElement(function.name) { FuncKeyboardEvent.AppendFunction(function) }
            }
            addMathElement(EXP_SYMBOL) { FuncKeyboardEvent.AppendExp }
            addMathElement(OPEN_BRACKET_SYMBOL) { FuncKeyboardEvent.AppendBracket(MathBracket.OPEN) }
            addMathElement(CLOSE_BRACKET_SYMBOL) { FuncKeyboardEvent.AppendBracket(MathBracket.CLOSE) }
            addMathElement("E") { FuncKeyboardEvent.AppendScientificNotation }
            // TODO: percent operator
            // TODO: ! operator
            selectableHeaderEpoxyView {
                id("constants")
                headerTitle("Constants")
            }
            constants.forEach { const ->
                addMathElement(const.name) { FuncKeyboardEvent.AppendConstant(const) }
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
