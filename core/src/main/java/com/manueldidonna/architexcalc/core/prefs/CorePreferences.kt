package com.manueldidonna.architexcalc.core.prefs

object CorePreferences {
    val ASK_TO_CLEAR_HISTORY = Preferences.BooleanPreference("ask_confirm", defaultValue = true)
    val DOUBLE_TAP_TO_EXIT_APP = Preferences.BooleanPreference("double_tap_exit")
}