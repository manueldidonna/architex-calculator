package com.manueldidonna.architexcalc.settings.ui

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.manueldidonna.arch.view.*
import com.manueldidonna.architexcalc.core.extensions.find
import com.manueldidonna.architexcalc.settings.R

internal sealed class SettingsEvent {
    data class AskToClearHistory(val value: Boolean) : SettingsEvent()
    data class TapBackToDelete(val value: Boolean) : SettingsEvent()
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
    private val tapBackToDelete = view.find<SwitchMaterial>(R.id.settings_tap_back_delete)
    private val askToClearHistorySwitch = view.find<SwitchMaterial>(R.id.settings_ask_clearing_history)
    private val autoClearHistoryButton = view.find<View>(R.id.settings_auto_clear_history)
    private val autoClearHistoryValueView = view.find<AppCompatTextView>(R.id.settings_auto_clear_value)

    init {
        emitOnClick(autoClearHistoryButton, SettingsEvent.AutoClearHistory)
        emitOnClick(appThemeButton, SettingsEvent.ShowAppThemeEditor)
        emitOnClick(showLicensesButton, SettingsEvent.ShowLicenses)
        emitOnClick(openGithubPageButton, SettingsEvent.OpenGithubPage)
        emitAndBindSwitch(doubleTapToExitSwitch, SettingsState::doubleTapToExit) { isChecked ->
            SettingsEvent.DoubleTapToExit(isChecked)
        }
        emitAndBindSwitch(tapBackToDelete, SettingsState::tapBackToDelete) { isChecked ->
            SettingsEvent.TapBackToDelete(isChecked)
        }
        emitAndBindSwitch(askToClearHistorySwitch, SettingsState::askToClearHistory) { isChecked ->
            SettingsEvent.AskToClearHistory(isChecked)
        }
        bind(SettingsState::autoClearHistoryDays) { days ->
            autoClearHistoryValueView.text = autoClearHistoryValues[SettingsState.getIndexFromDays(days)]
        }
    }

    private inline fun emitAndBindSwitch(
        view: SwitchMaterial,
        noinline propToBind: (SettingsState) -> Boolean,
        crossinline eventToEmit: (Boolean) -> SettingsEvent
    ) {
        view.setOnCheckedChangeListener { _, isChecked ->
            emitEvent(eventToEmit(isChecked))
        }
        bind(propToBind) { newValue ->
            view.isChecked = newValue
        }
    }
}