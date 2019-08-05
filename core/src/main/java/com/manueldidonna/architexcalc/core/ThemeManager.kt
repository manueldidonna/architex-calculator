package com.manueldidonna.architexcalc.core

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.BuildCompat
import com.manueldidonna.architexcalc.core.prefs.Preferences
import com.manueldidonna.architexcalc.core.prefs.PreferencesManager
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import timber.log.Timber

class ThemeManager(private val prefsManager: PreferencesManager) {

    private val disposables = CompositeDisposable()

    fun init() {
        observe().subscribe { savedMode ->
            AppCompatDelegate.setDefaultNightMode(savedMode.compatThemeMode())
            Timber.d("Theme mode updated: $savedMode")
        }.addTo(disposables)
    }

    private fun String.compatThemeMode(): Int = when (this) {
        DARK_MODE -> AppCompatDelegate.MODE_NIGHT_YES
        LIGHT_MODE -> AppCompatDelegate.MODE_NIGHT_NO
        else -> {
            if (BuildCompat.isAtLeastQ())
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            else
                AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
        }
    }

    fun observe(): Observable<String> = prefsManager.observe(SELECTED_THEME)

    fun enableDarkMode(enable: Boolean) {
        prefsManager.set(SELECTED_THEME, if (enable) DARK_MODE else LIGHT_MODE)
    }

    fun enableDefaultMode() {
        prefsManager.set(SELECTED_THEME, DEFAULT_MODE)
    }

    fun isDarkEnabled(): Boolean = prefsManager.get(SELECTED_THEME) == DARK_MODE

    fun isDefaultEnabled(): Boolean = prefsManager.get(SELECTED_THEME) == DEFAULT_MODE

    companion object {
        const val DARK_MODE = "theme:dark_mode"
        const val LIGHT_MODE = "theme:light_mode"
        const val DEFAULT_MODE = "theme:default"

        private val SELECTED_THEME = Preferences.StringPreference("selected_theme", LIGHT_MODE)
    }
}