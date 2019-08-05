package com.manueldidonna.architexcalc.core.prefs

import android.content.Context
import com.afollestad.rxkprefs.rxkPrefs
import io.reactivex.Observable

class RealPreferencesManager(context: Context, val name: String = NAME) : PreferencesManager {

    private val prefs = rxkPrefs(context, name)

    override fun <T> get(pref: Preferences<T>): T = pref.get(prefs)

    override fun <T> set(pref: Preferences<T>, value: T) = pref.set(prefs, value)

    override fun <T> observe(pref: Preferences<T>): Observable<T> {
        return pref.observe(prefs).mergeWith(Observable.just(pref.get(prefs)))
    }

    companion object {
        private const val NAME = "architexcalc_prefs"
    }
}