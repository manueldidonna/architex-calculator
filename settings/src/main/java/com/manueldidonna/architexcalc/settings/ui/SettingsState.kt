package com.manueldidonna.architexcalc.settings.ui

import com.manueldidonna.arch.state.ArxState

data class SettingsState(
    val askToClearHistory: Boolean = true,
    val autoClearHistoryDays: Int = 0,
    val doubleTapToExit: Boolean = false,
    val tapBackToDelete: Boolean = false
) : ArxState {
    companion object {
        fun getDaysFromIndex(index: Int) = when (index) {
            0 -> 0
            1 -> 1
            2 -> 7
            3 -> 30
            else -> throw IllegalStateException("Custom values are not allowed")
        }

        fun getIndexFromDays(days: Int): Int = when (days) {
            0 -> 0
            1 -> 1
            7 -> 2
            30 -> 3
            else -> throw IllegalStateException("Custom values are not allowed")
        }
    }
}