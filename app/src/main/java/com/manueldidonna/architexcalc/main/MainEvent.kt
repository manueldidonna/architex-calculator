package com.manueldidonna.architexcalc.main

sealed class MainEvent {
    object History : MainEvent()
    object Settings : MainEvent()
    data class CopyToClipboard(val label: String, val text: String) : MainEvent()
}