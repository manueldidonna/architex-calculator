package com.manueldidonna.architexcalc.settings

import androidx.work.BackoffPolicy
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.manueldidonna.architexcalc.core.base.BaseViewModel
import com.manueldidonna.architexcalc.core.prefs.CorePreferences.ASK_CONFIRM
import com.manueldidonna.architexcalc.core.prefs.CorePreferences.DOUBLE_TAP_EXIT
import com.manueldidonna.architexcalc.core.prefs.Preferences
import com.manueldidonna.architexcalc.core.prefs.PreferencesManager
import com.manueldidonna.architexcalc.settings.data.SettingsPreferences.AUTO_CLEAR_HISTORY_DAYS
import com.manueldidonna.architexcalc.settings.data.workers.AutoClearHistoryWorker
import java.util.concurrent.TimeUnit

class SettingsViewModel(
    initialState: SettingsState,
    private val prefsManager: PreferencesManager
) : BaseViewModel<SettingsState>(initialState) {

    init {
        prefsManager.apply {
            // Observe for state changes
            subscribe(ASK_CONFIRM) { newValue -> copy(askConfirmation = newValue) }
            subscribe(AUTO_CLEAR_HISTORY_DAYS) { newValue -> copy(autoClearHistoryDays = newValue) }
            subscribe(DOUBLE_TAP_EXIT) { newValue -> copy(doubleTapToExit = newValue) }
        }
    }

    private inline fun <T> PreferencesManager.subscribe(
        pref: Preferences<T>,
        crossinline block: SettingsState.(newValue: T) -> SettingsState
    ) = observe(pref).subscribe { newValue ->
        setState { block(newValue) }
    }.disposeOnClear()


    fun setAskConfirmation(value: Boolean) {
        prefsManager.set(ASK_CONFIRM, value)
    }

    fun setDoubleTapToExit(value: Boolean) {
        prefsManager.set(DOUBLE_TAP_EXIT, value)
    }

    fun setAutoClearHistoryDays(days: Int) {
        require(days == 0 || days == 1 || days == 7 || days == 30) { "Custom values are not accepted" }
        prefsManager.set(AUTO_CLEAR_HISTORY_DAYS, days)

        if (days == 0) {
            WorkManager.getInstance().cancelUniqueWork(AutoClearHistoryWorker.TAG)
        } else {
            val req = PeriodicWorkRequestBuilder<AutoClearHistoryWorker>(days.toLong(), TimeUnit.DAYS)
                .addTag(AutoClearHistoryWorker.TAG)
                .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 15L, TimeUnit.MINUTES)
                .build()
            WorkManager
                .getInstance()
                .enqueueUniquePeriodicWork(AutoClearHistoryWorker.TAG, ExistingPeriodicWorkPolicy.REPLACE, req)
        }
    }

    // TODO: remove fakeSubscribe
    fun fakeSubscribe(subscriber: (SettingsState) -> Unit) {
        subscribe(subscriber = subscriber)
    }

}