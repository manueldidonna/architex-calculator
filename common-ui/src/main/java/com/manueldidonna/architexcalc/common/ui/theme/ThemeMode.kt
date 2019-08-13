package com.manueldidonna.architexcalc.common.ui.theme

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.BuildCompat

enum class ThemeMode(internal val key: String) {
    LIGHT("theme:light_mode"),
    DARK("theme:dark_mode"),
    DEFAULT("theme:default_mode")
}

internal fun String.toThemeMode(): ThemeMode = when(this){
    ThemeMode.DARK.key -> ThemeMode.DARK
    ThemeMode.LIGHT.key -> ThemeMode.LIGHT
    else -> ThemeMode.DEFAULT
}

internal fun ThemeMode.toAppCompatMode() = when (this) {
    ThemeMode.DARK -> AppCompatDelegate.MODE_NIGHT_YES
    ThemeMode.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
    else -> {
        if (BuildCompat.isAtLeastQ())
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        else
            AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
    }
}

