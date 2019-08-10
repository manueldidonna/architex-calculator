package com.manueldidonna.architexcalc.core

import com.manueldidonna.architexcalc.core.prefs.PreferencesManager
import com.manueldidonna.architexcalc.core.prefs.RealPreferencesManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreKoinModule = module(createdAtStart = true, override = true) {
    single<PreferencesManager> { RealPreferencesManager(androidContext()) }
}