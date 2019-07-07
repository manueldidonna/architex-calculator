package com.manueldidonna.architexcalc.settings

import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import butterknife.BindArray
import butterknife.BindString
import butterknife.BindView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.manueldidonna.arch.view.*
import com.manueldidonna.architexcalc.core.base.BaseContainerView

class SettingsView(view: View) : BaseContainerView(view),
    ArxBinderView<SettingsState> by ArxStateBinderView(),
    ArxInteractiveView<SettingsEvent> by ArxSubjectView() {
    // Resources
    @BindString(R2.string.settings_toolbar_title)
    lateinit var settingsTitleValue: String
    @BindArray(R2.array.settings_auto_clear_history_values)
    lateinit var autoClearHistoryValues: Array<String>
    // Views
    @BindView(R2.id.toolbar_navigation_icon)
    lateinit var navigationIcon: AppCompatImageView
    @BindView(R2.id.toolbar_title)
    lateinit var toolbarTitle: AppCompatTextView
    @BindView(R2.id.settings_licenses)
    lateinit var licensesButton: AppCompatTextView
    @BindView(R2.id.settings_github)
    lateinit var githubButton: LinearLayout
    @BindView(R2.id.settings_ask_clearing_history)
    lateinit var askClearHistorySwitch: SwitchMaterial
    @BindView(R2.id.settings_auto_clear_history)
    lateinit var autoClearHistoryButton: View
    @BindView(R2.id.settings_auto_clear_value)
    lateinit var autoClearHistoryValue: AppCompatTextView
    @BindView(R2.id.settings_app_theme)
    lateinit var appThemeButton: AppCompatTextView
    @BindView(R2.id.settings_double_tap_exit)
    lateinit var doubleTapExitSwitch: SwitchMaterial

    init {
        toolbarTitle.text = settingsTitleValue
        emitOnClick(navigationIcon, SettingsEvent.GoBack)
        emitOnClick(autoClearHistoryButton, SettingsEvent.AutoClearHistory)
        emitOnClick(appThemeButton, SettingsEvent.AppThemeEditor)
        emitOnClick(licensesButton, SettingsEvent.Licenses)
        emitOnClick(githubButton, SettingsEvent.GithubPage)
        askClearHistorySwitch.setOnCheckedChangeListener { _, isChecked ->
            emitEvent(SettingsEvent.AskClearHistoryConfirmation(isChecked))
        }
        doubleTapExitSwitch.setOnCheckedChangeListener { _, isChecked ->
            emitEvent(SettingsEvent.DoubleTapExit(isChecked))
        }

        bind(SettingsState::askConfirmation) { askConfirm ->
            askClearHistorySwitch.isChecked = askConfirm
        }
        bind(SettingsState::autoClearHistoryDays) { days ->
            autoClearHistoryValue.text = autoClearHistoryValues[SettingsState.getIndexFromDays(days)]
        }
        bind(SettingsState::doubleTapToExit) { doubleTap ->
            doubleTapExitSwitch.isChecked = doubleTap
        }
    }
}