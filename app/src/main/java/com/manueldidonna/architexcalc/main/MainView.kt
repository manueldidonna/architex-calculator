package com.manueldidonna.architexcalc.main

import android.view.View
import android.widget.Button
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import butterknife.BindString
import butterknife.BindView
import com.manueldidonna.arch.view.*
import com.manueldidonna.architexcalc.R
import com.manueldidonna.architexcalc.core.base.BaseContainerView
import com.manueldidonna.architexcalc.core.ui.HorizontalScrollTextView
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class MainView(view: View, adapter: PagerAdapter) : BaseContainerView(view),
    ArxBinderView<MainState> by ArxStateBinderView(),
    ArxInteractiveView<MainEvent> by ArxSubjectView() {

    @BindString(R.string.calculator_panel_error)
    lateinit var panelErrorMessage: String
    @BindView(R.id.keyboard_view_pager)
    lateinit var keyboardPager: ViewPager
    @BindView(R.id.pager_indicator)
    lateinit var wormDotsIndicator: WormDotsIndicator
    @BindView(R.id.history_button)
    lateinit var historyButton: Button
    @BindView(R.id.settings_button)
    lateinit var settingsButton: Button

    private val scrollableExpression: HorizontalScrollTextView by findView(R.id.scrollable_expression)
    private val scrollableResult: HorizontalScrollTextView by findView(R.id.scrollable_result)

    init {
        keyboardPager.adapter = adapter
        wormDotsIndicator.setViewPager(keyboardPager)
        // Event emissions
        emitOnClick(historyButton, MainEvent.History)
        emitOnClick(settingsButton, MainEvent.Settings)
        scrollableExpression.onChildLongClickListener {
            emitEvent(MainEvent.CopyPasteExpression(scrollableExpression.text.toString()))
            return@onChildLongClickListener true
        }
        scrollableResult.onChildLongClickListener {
            val textToCopy = scrollableResult.text.toString()
            if (textToCopy.isEmpty() || textToCopy == panelErrorMessage)
                return@onChildLongClickListener false
            emitEvent(MainEvent.CopyResult(textToCopy))
            return@onChildLongClickListener true
        }

        // Bind view properties
        bind(MainState::expression) { expr -> scrollableExpression.updateText(expr) }
        bind({ Pair(it.result, it.errorOnResult) }) { (result, errorOnResult) ->
            scrollableResult.text = if (errorOnResult) panelErrorMessage else result
        }
    }
}