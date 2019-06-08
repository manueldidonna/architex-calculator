package com.manueldidonna.architexcalc.main

import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import butterknife.BindString
import butterknife.BindView
import com.manueldidonna.architexcalc.R
import com.manueldidonna.architexcalc.core.base.BaseContainerView
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class MainView(view: View, adapter: PagerAdapter) : BaseContainerView<MainState, MainEvent>(view) {

    @BindString(R.string.calculator_panel_error)
    lateinit var panelErrorMessage: String

    @BindView(R.id.expression_text_view)
    lateinit var expressionView: TextView
    @BindView(R.id.result_text_view)
    lateinit var resultView: TextView
    @BindView(R.id.keyboard_view_pager)
    lateinit var keyboardPager: ViewPager
    @BindView(R.id.pager_indicator)
    lateinit var wormDotsIndicator: WormDotsIndicator
    @BindView(R.id.history_button)
    lateinit var historyButton: Button
    @BindView(R.id.settings_button)
    lateinit var settingsButton: Button

    init {
        keyboardPager.adapter = adapter
        wormDotsIndicator.setViewPager(keyboardPager)
        // Event emissions
        historyButton.emitOnClick(MainEvent.History)
        settingsButton.emitOnClick(MainEvent.Settings)
        expressionView.apply {
            movementMethod = ScrollingMovementMethod()
            setOnLongClickListener {
                emit(MainEvent.CopyPasteExpression(text.toString()))
                return@setOnLongClickListener true
            }
        }
        resultView.apply {
            movementMethod = ScrollingMovementMethod()
            setOnLongClickListener {
                val textToCopy = text.toString()
                if (textToCopy.isEmpty() || textToCopy == panelErrorMessage)
                    return@setOnLongClickListener false
                emit(MainEvent.CopyResult(textToCopy))
                return@setOnLongClickListener true
            }
        }

        // Bind view properties
        bind(MainState::expression) { expr -> expressionView.text = expr }
        bind({ Pair(it.result, it.errorOnResult) }) { (result, errorOnResult) ->
            resultView.text = if (errorOnResult) panelErrorMessage else result
        }
    }
}