package com.manueldidonna.architexcalc.core.prefs

import com.afollestad.rxkprefs.RxkPrefs
import io.reactivex.Observable

sealed class Preferences<T>(val key: String) {
    internal abstract fun observe(prefs: RxkPrefs): Observable<T>
    internal abstract fun get(prefs: RxkPrefs): T
    internal abstract fun set(prefs: RxkPrefs, value: T)

    class IntPreference(key: String, private val defaultValue: Int = 0) : Preferences<Int>(key) {
        override fun observe(prefs: RxkPrefs) = prefs.integer(key).observe()
        override fun get(prefs: RxkPrefs): Int = prefs.integer(key, defaultValue).get()
        override fun set(prefs: RxkPrefs, value: Int) {
            prefs.integer(key).set(value)
        }
    }

    class BooleanPreference(key: String, private val defaultValue: Boolean = false) : Preferences<Boolean>(key) {
        override fun observe(prefs: RxkPrefs) = prefs.boolean(key).observe()
        override fun get(prefs: RxkPrefs): Boolean = prefs.boolean(key, defaultValue).get()
        override fun set(prefs: RxkPrefs, value: Boolean) {
            prefs.boolean(key).set(value)
        }
    }

    class StringPreference(key: String, private val defaultValue: String = "") : Preferences<String>(key) {
        override fun observe(prefs: RxkPrefs) = prefs.string(key).observe()
        override fun get(prefs: RxkPrefs): String = prefs.string(key, defaultValue).get()
        override fun set(prefs: RxkPrefs, value: String) {
            prefs.string(key).set(value)
        }
    }
}