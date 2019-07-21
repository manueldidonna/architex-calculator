package com.manueldidonna.architex.calculator.ui

import android.view.View
import android.widget.Button
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.manueldidonna.arch.view.*
import com.manueldidonna.architex.calculator.R
import com.manueldidonna.architexcalc.core.extensions.find
import com.manueldidonna.architexcalc.core.ui.HorizontalScrollTextView
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

internal sealed class CalculatorEvent {
    object ShowHistory : CalculatorEvent()
    object ShowSettings : CalculatorEvent()
    data class CopyPasteExpression(val text: String) : CalculatorEvent()
    data class CopyResult(val text: String) : CalculatorEvent()
}

internal class CalculatorView(view: View, adapter: PagerAdapter) : ArxBinderView<CalculatorState> by ArxStateBinderView(),
    ArxInteractiveView<CalculatorEvent> by ArxSubjectView() {

    private val panelErrorMessage = view.resources.getString(R.string.calculator_panel_error)

    private val keyboardPager = view.find<ViewPager>(R.id.keyboard_view_pager)
    private val historyButton = view.find<Button>(R.id.history_button)
    private val settingsButton = view.find<Button>(R.id.settings_button)
    private val scrollableExpression = view.find<HorizontalScrollTextView>(R.id.scrollable_expression)
    private val scrollableResult = view.find<HorizontalScrollTextView>(R.id.scrollable_result)

    init {
        keyboardPager.adapter = adapter
        view.find<WormDotsIndicator>(R.id.pager_indicator).setViewPager(keyboardPager)
        // Event emissions
        emitOnClick(historyButton, CalculatorEvent.ShowHistory)
        emitOnClick(settingsButton, CalculatorEvent.ShowSettings)
        scrollableExpression.onChildLongClickListener {
            emitEvent(CalculatorEvent.CopyPasteExpression(scrollableExpression.text.toString()))
            return@onChildLongClickListener true
        }
        scrollableResult.onChildLongClickListener {
            val textToCopy = scrollableResult.text.toString()
            if (textToCopy.isEmpty() || textToCopy == panelErrorMessage)
                return@onChildLongClickListener false
            emitEvent(CalculatorEvent.CopyResult(textToCopy))
            return@onChildLongClickListener true
        }
        // Bind view properties
        bind(CalculatorState::expression) { expr -> scrollableExpression.updateText(expr) }
        bind({ Pair(it.result, it.errorOnResult) }) { (result, errorOnResult) ->
            scrollableResult.text = if (errorOnResult) panelErrorMessage else result
        }
    }
}