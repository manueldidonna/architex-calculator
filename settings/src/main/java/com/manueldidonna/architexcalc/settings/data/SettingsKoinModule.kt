package com.manueldidonna.architexcalc.settings.data

import com.manueldidonna.architexcalc.settings.SettingsState
import com.manueldidonna.architexcalc.settings.SettingsViewModel
import org.koin.dsl.module

val settingsKoinModule = module(override = true) {
    scoped { (state: SettingsState) -> SettingsViewModel(initialState = state, prefsManager = get()) }
}