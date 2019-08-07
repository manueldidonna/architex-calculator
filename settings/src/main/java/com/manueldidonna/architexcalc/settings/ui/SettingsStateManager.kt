package com.manueldidonna.architexcalc.settings.ui

import com.manueldidonna.architexcalc.core.base.BaseStateManager
import com.manueldidonna.architexcalc.core.launchInteractor
import com.manueldidonna.architexcalc.core.prefs.CorePreferences.ASK_TO_CLEAR_HISTORY
import com.manueldidonna.architexcalc.core.prefs.CorePreferences.DOUBLE_TAP_TO_EXIT_APP
import com.manueldidonna.architexcalc.core.prefs.CorePreferences.TAP_BACK_TO_DELETE
import com.manueldidonna.architexcalc.core.prefs.Preferences
import com.manueldidonna.architexcalc.core.prefs.PreferencesManager
import com.manueldidonna.architexcalc.settings.domain.AutoClearHistoryInteractor
import com.manueldidonna.architexcalc.settings.SettingsPreferences.AUTO_CLEAR_HISTORY_DAYS

class SettingsStateManager(
    initialState: SettingsState,
    private val prefsManager: PreferencesManager,
    private val autoClearHistory: AutoClearHistoryInteractor
) : BaseStateManager<SettingsState>(initialState) {

    init {
        prefsManager.apply {
            // Observe for state changes
            subscribe(ASK_TO_CLEAR_HISTORY) { newValue -> copy(askToClearHistory = newValue) }
            subscribe(AUTO_CLEAR_HISTORY_DAYS) { newValue -> copy(autoClearHistoryDays = newValue) }
            subscribe(DOUBLE_TAP_TO_EXIT_APP) { newValue -> copy(doubleTapToExit = newValue) }
            subscribe(TAP_BACK_TO_DELETE) { newValue -> copy(tapBackToDelete = newValue) }
        }
    }

    private inline fun <T> PreferencesManager.subscribe(
        pref: Preferences<T>,
        crossinline block: SettingsState.(newValue: T) -> SettingsState
    ) = observe(pref).autoSubscribe { newValue -> setState { block(newValue) } }

    fun setAskConfirmation(value: Boolean) {
        prefsManager.set(ASK_TO_CLEAR_HISTORY, value)
    }

    fun setTapBackToDelete(value: Boolean){
        prefsManager.set(TAP_BACK_TO_DELETE, value)
    }

    fun setDoubleTapToExit(value: Boolean) {
        prefsManager.set(DOUBLE_TAP_TO_EXIT_APP, value)
    }

    fun setAutoClearHistoryDays(days: Int) {
        require(days == 0 || days == 1 || days == 7 || days == 30) { "Custom values are not accepted" }
        prefsManager.set(AUTO_CLEAR_HISTORY_DAYS, days)
        stateManagerScope.launchInteractor(autoClearHistory, days.toLong())
    }
}