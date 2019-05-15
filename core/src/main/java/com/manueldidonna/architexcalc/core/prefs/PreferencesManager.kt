package com.manueldidonna.architexcalc.core.prefs

import io.reactivex.Observable


interface PreferencesManager {

    fun <T> get(pref: Preferences<T>): T

    fun <T> set(pref: Preferences<T>, value: T)

    fun <T> observe(pref: Preferences<T>): Observable<T>
}