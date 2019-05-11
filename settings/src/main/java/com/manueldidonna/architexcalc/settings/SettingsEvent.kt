package com.manueldidonna.architexcalc.settings

sealed class SettingsEvent {
    object GoBack : SettingsEvent()
    data class AskClearHistoryConfirmation(val value: Boolean) : SettingsEvent()
    data class DoubleTapExit(val value: Boolean): SettingsEvent()
    object AutoClearHistory : SettingsEvent()
    object AppThemeEditor : SettingsEvent()
    object Licenses : SettingsEvent()
    object GithubPage: SettingsEvent()
}