package com.manueldidonna.architexcalc.settings.ui

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.manueldidonna.arch.view.*
import com.manueldidonna.architexcalc.core.extensions.find
import com.manueldidonna.architexcalc.settings.R

internal sealed class SettingsEvent {
    data class AskToClearHistory(val value: Boolean) : SettingsEvent()
    data class DoubleTapToExit(val value: Boolean) : SettingsEvent()
    object AutoClearHistory : SettingsEvent()
    object OpenGithubPage : SettingsEvent()
    object ShowLicenses : SettingsEvent()
    object ShowAppThemeEditor : SettingsEvent()
}

internal class SettingsView(view: View) : ArxInteractiveView<SettingsEvent> by ArxSubjectView(),
    ArxBinderView<SettingsState> by ArxStateBinderView() {

    private val autoClearHistoryValues = view.resources.getStringArray(R.array.settings_auto_clear_history_values)

    private val appThemeButton = view.find<View>(R.id.settings_app_theme)
    private val showLicensesButton = view.find<View>(R.id.settings_licenses)
    private val openGithubPageButton = view.find<View>(R.id.settings_github)
    private val doubleTapToExitSwitch = view.find<SwitchMaterial>(R.id.settings_double_tap_exit)
    private val askToClearHistorySwitch = view.find<SwitchMaterial>(R.id.settings_ask_clearing_history)
    private val autoClearHistoryButton = view.find<View>(R.id.settings_auto_clear_history)
    private val autoClearHistoryValueView = view.find<AppCompatTextView>(R.id.settings_auto_clear_value)

    init {
        emitOnClick(autoClearHistoryButton, SettingsEvent.AutoClearHistory)
        emitOnClick(appThemeButton, SettingsEvent.ShowAppThemeEditor)
        emitOnClick(showLicensesButton, SettingsEvent.ShowLicenses)
        emitOnClick(openGithubPageButton, SettingsEvent.OpenGithubPage)
        doubleTapToExitSwitch.setOnCheckedChangeListener { _, isChecked ->
            emitEvent(SettingsEvent.DoubleTapToExit(isChecked))
        }
        askToClearHistorySwitch.setOnCheckedChangeListener { _, isChecked ->
            emitEvent(SettingsEvent.AskToClearHistory(isChecked))
        }
        bind(SettingsState::askToClearHistory) { askConfirm ->
            askToClearHistorySwitch.isChecked = askConfirm
        }
        bind(SettingsState::autoClearHistoryDays) { days ->
            autoClearHistoryValueView.text = autoClearHistoryValues[SettingsState.getIndexFromDays(days)]
        }
        bind(SettingsState::doubleTapToExit) { doubleTap ->
            doubleTapToExitSwitch.isChecked = doubleTap
        }
    }
}