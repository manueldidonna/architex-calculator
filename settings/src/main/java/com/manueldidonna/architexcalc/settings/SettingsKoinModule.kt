package com.manueldidonna.architexcalc.settings

import androidx.work.WorkManager
import com.manueldidonna.architexcalc.settings.domain.AutoClearHistoryInteractor
import com.manueldidonna.architexcalc.settings.ui.SettingsState
import com.manueldidonna.architexcalc.settings.ui.SettingsStateManager
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val settingsKoinModule = module(override = true) {
    factory { WorkManager.getInstance(androidApplication()) }
    factory { AutoClearHistoryInteractor(workManager = get()) }
    scoped {
        SettingsStateManager(
            initialState = SettingsState(),
            prefsManager = get(),
            autoClearHistory = get()
        )
    }
}