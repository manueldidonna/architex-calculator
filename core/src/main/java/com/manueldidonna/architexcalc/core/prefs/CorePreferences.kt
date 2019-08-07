package com.manueldidonna.architexcalc.core.prefs

object CorePreferences {
    val ASK_TO_CLEAR_HISTORY = Preferences.BooleanPreference("ask_confirm", defaultValue = true)
    val DOUBLE_TAP_TO_EXIT_APP = Preferences.BooleanPreference("double_tap_exit")
    val TAP_BACK_TO_DELETE = Preferences.BooleanPreference("back_to_del", defaultValue = false)
}