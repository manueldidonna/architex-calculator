package com.manueldidonna.architexcalc.settings.data

import androidx.work.WorkManager
import com.manueldidonna.architexcalc.settings.SettingsState
import com.manueldidonna.architexcalc.settings.SettingsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val settingsKoinModule = module(override = true) {
    factory { WorkManager.getInstance(androidApplication()) }
    factory { AutoClearHistoryInteractor(workManager = get()) }
    scoped { (state: SettingsState) ->
        SettingsViewModel(
            initialState = state,
            prefsManager = get(),
            autoClearHistory = get()
        )
    }
}