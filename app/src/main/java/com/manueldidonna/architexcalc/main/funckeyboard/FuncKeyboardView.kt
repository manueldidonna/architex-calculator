package com.manueldidonna.architexcalc.main.funckeyboard

import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.manueldidonna.arch.view.ArxInteractiveView
import com.manueldidonna.arch.view.createSubject
import com.manueldidonna.architexcalc.R
import io.reactivex.Observable

class FuncKeyboardView(
    view: View,
    funcs: List<String>,
    constants: List<String>
) : ArxInteractiveView<FuncKeyboardEvent> {
    private val subject = createSubject()

    init {
        view.findViewById<EpoxyRecyclerView>(R.id.func_recycler).withModels {
            iterateWithHeader("Math functions", funcs) {}
            iterateWithHeader("Constants", constants) {}
        }
    }

    private inline fun EpoxyController.iterateWithHeader(
        title: String,
        elements: List<String>,
        crossinline listener: () -> Unit
    ) {
        selectableHeaderEpoxyView {
            id(title)
            headerTitle(title)
        }
        elements.forEachIndexed { index, s ->
            mathElementEpoxyView {
                id("math.elem.$title.$index")
                elementName(s)
                listener { _ -> listener() }
            }
        }
    }

    override fun getUserInteractionEvents(): Observable<FuncKeyboardEvent> = subject
}
