package com.manueldidonna.architexcalc.common.ui.theme

import androidx.appcompat.app.AppCompatDelegate
import com.manueldidonna.architexcalc.core.prefs.Preferences
import com.manueldidonna.architexcalc.core.prefs.PreferencesManager
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class ThemeManager(private val prefsManager: PreferencesManager) {

    private val disposables = CompositeDisposable()

    fun init() {
        disposables.add(
            observeSelectedThemeMode().subscribe { savedMode ->
                AppCompatDelegate.setDefaultNightMode(savedMode.toAppCompatMode())
                Timber.d("Theme mode updated: ${savedMode.key}")
            }
        )
    }

    fun observeSelectedThemeMode(): Observable<ThemeMode> = prefsManager
        .observe(SELECTED_THEME)
        .map(String::toThemeMode)

    var selectedThemeMode: ThemeMode
        set(value) = prefsManager.set(SELECTED_THEME, value.key)
        get() = when (prefsManager.get(SELECTED_THEME)) {
            ThemeMode.DARK.key -> ThemeMode.DARK
            ThemeMode.DEFAULT.key -> ThemeMode.DEFAULT
            else -> ThemeMode.LIGHT
        }

    companion object {
        private val SELECTED_THEME = Preferences.StringPreference("selected_theme", ThemeMode.LIGHT.key)
    }
}