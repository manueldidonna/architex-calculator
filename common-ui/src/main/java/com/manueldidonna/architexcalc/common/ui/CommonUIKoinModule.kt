package com.manueldidonna.architexcalc.common.ui

import org.koin.dsl.module

val commonUIKoinModule = module(createdAtStart = true, override = true) {
    single { ThemeManager(prefsManager = get()) }
}