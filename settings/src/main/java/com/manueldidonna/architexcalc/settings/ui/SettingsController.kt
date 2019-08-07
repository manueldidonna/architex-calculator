package com.manueldidonna.architexcalc.settings.ui

import android.content.Intent
import android.view.View
import androidx.core.net.toUri
import com.manueldidonna.arch.conductorLazy
import com.manueldidonna.arch.controller.getString
import com.manueldidonna.arch.statemanager.withState
import com.manueldidonna.architexcalc.core.base.BaseStatefulController
import com.manueldidonna.architexcalc.core.extensions.pushWithStandardTransaction
import com.manueldidonna.architexcalc.settings.R
import com.manueldidonna.architexcalc.settings.ui.autoclearhistory.AutoClearHistoryDialog
import com.manueldidonna.architexcalc.settings.ui.licenses.LicensesController
import com.manueldidonna.architexcalc.settings.ui.shared.ToolbarView
import com.manueldidonna.architexcalc.settings.ui.themeselector.ThemeSelectorDialog

class SettingsController : BaseStatefulController<SettingsStateManager, SettingsState>(),
    AutoClearHistoryDialog.Listener {

    override val saveStateOnProcessDeath = false

    override val stateManager by conductorLazy { controllerScope.get<SettingsStateManager>() }

    override val layoutRes = R.layout.controller_settings

    override fun onViewCreated(view: View) {
        super.onViewCreated(view)
        addView(ToolbarView(view, getString(R.string.settings_toolbar_title)))
            .onEvents { router.popController(this) }
        addView(SettingsView(view))
            .subscribeToStateChanges()
            .onEvents { event ->
                when (event) {
                    is SettingsEvent.TapBackToDelete -> stateManager.setTapBackToDelete(event.value)
                    is SettingsEvent.DoubleTapToExit -> stateManager.setDoubleTapToExit(event.value)
                    is SettingsEvent.AskToClearHistory -> stateManager.setAskConfirmation(event.value)
                    is SettingsEvent.AutoClearHistory -> showAutoClearHistoryDialog()
                    is SettingsEvent.ShowLicenses -> router.pushWithStandardTransaction(LicensesController())
                    is SettingsEvent.ShowAppThemeEditor -> ThemeSelectorDialog.show(router)
                    is SettingsEvent.OpenGithubPage -> startActivity(
                        Intent(Intent.ACTION_VIEW).setData(getString(R.string.settings_github_link).toUri())
                    )
                }
            }
    }

    private fun showAutoClearHistoryDialog() = withState { state ->
        val index = SettingsState.getIndexFromDays(state.autoClearHistoryDays)
        AutoClearHistoryDialog.show(router, this@SettingsController, index)
    }

    override fun onAutoClearHistoryCallback(days: Int) {
        stateManager.setAutoClearHistoryDays(days)
    }
}