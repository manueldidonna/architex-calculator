package com.manueldidonna.architexcalc.core.prefs

import com.afollestad.rxkprefs.RxkPrefs
import io.reactivex.Observable

sealed class Preferences<T>(val key: String) {
    internal abstract fun observe(prefs: RxkPrefs): Observable<T>
    internal abstract fun get(prefs: RxkPrefs, defaultValue: T): T
    internal abstract fun get(prefs: RxkPrefs): T
    internal abstract fun set(prefs: RxkPrefs, value: T)

    class IntPreference(key: String) : Preferences<Int>(key) {
        override fun observe(prefs: RxkPrefs) = prefs.integer(key).observe()
        override fun get(prefs: RxkPrefs, defaultValue: Int): Int = prefs.integer(key, defaultValue).get()
        override fun get(prefs: RxkPrefs): Int = prefs.integer(key).get()
        override fun set(prefs: RxkPrefs, value: Int) {
            prefs.integer(key).set(value)
        }
    }

    class BooleanPreference(key: String) : Preferences<Boolean>(key) {
        override fun observe(prefs: RxkPrefs) = prefs.boolean(key).observe()
        override fun get(prefs: RxkPrefs, defaultValue: Boolean): Boolean = prefs.boolean(key, defaultValue).get()
        override fun get(prefs: RxkPrefs): Boolean = prefs.boolean(key).get()
        override fun set(prefs: RxkPrefs, value: Boolean) {
            prefs.boolean(key).set(value)
        }
    }

    class StringPreference(key: String) : Preferences<String>(key) {
        override fun observe(prefs: RxkPrefs) = prefs.string(key).observe()
        override fun get(prefs: RxkPrefs, defaultValue: String): String = prefs.string(key, defaultValue).get()
        override fun get(prefs: RxkPrefs): String = prefs.string(key).get()
        override fun set(prefs: RxkPrefs, value: String) {
            prefs.string(key).set(value)
        }
    }
}