package com.manueldidonna.architexcalc.main

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import butterknife.BindString
import butterknife.BindView
import com.manueldidonna.arch.view.ArxInteractiveButterBinder
import com.manueldidonna.architexcalc.R
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class MainView(view: View, adapter: PagerAdapter) : ArxInteractiveButterBinder<MainState, MainEvent>(view) {

    @BindString(R.string.calculator_panel_error)
    lateinit var panelErrorMessage: String

    @BindView(R.id.expression_text_view)
    lateinit var expressionView: AppCompatEditText
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
        historyButton.setOnClickListener { emit(MainEvent.History) }
        settingsButton.setOnClickListener { emit(MainEvent.Settings) }
        expressionView.copyOnLongClick("MATH_EXPRESSION") { text.toString() }
        resultView.copyOnLongClick("RESULT_EXPRESSION") { text.toString() }
        // Bind view properties
        bind(MainState::expression) { expr -> expressionView.setText(expr) }
        bind({ Pair(it.result, it.errorOnResult) }) { (result, errorOnResult) ->
            resultView.text = if (errorOnResult) panelErrorMessage else result
        }
    }

    private inline fun <T : View> T.copyOnLongClick(label: String, crossinline text: T.() -> String) {
        setOnLongClickListener {
            val textToCopy = text()
            if (textToCopy.isEmpty() || textToCopy == panelErrorMessage)
                return@setOnLongClickListener false
            emit(MainEvent.CopyToClipboard(label, textToCopy))
            return@setOnLongClickListener true
        }
    }
}