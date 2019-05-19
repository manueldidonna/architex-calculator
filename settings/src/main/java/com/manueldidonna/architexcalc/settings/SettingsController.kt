package com.manueldidonna.architexcalc.settings

import android.content.Intent
import android.view.View
import androidx.core.net.toUri
import com.manueldidonna.arch.controller.requireActivity
import com.manueldidonna.arch.state.withState
import com.manueldidonna.arch.viewmodel.arxViewModel
import com.manueldidonna.architexcalc.core.base.BaseController
import com.manueldidonna.architexcalc.core.extensions.pushWithStandardTransaction
import com.manueldidonna.architexcalc.settings.autoclearhistory.AutoClearHistoryDialog
import com.manueldidonna.architexcalc.settings.licenses.LicensesController
import com.manueldidonna.architexcalc.settings.themeselector.ThemeSelectorDialog
import org.koin.core.parameter.parametersOf

class SettingsController : BaseController<SettingsView>(), AutoClearHistoryDialog.Listener {

    private val viewModel by arxViewModel(SettingsState()) { state ->
        controllerScope.get<SettingsViewModel> { parametersOf(state) }
    }

    override val layoutRes = R.layout.controller_settings

    override fun onCreateControllerView(layoutView: View) = SettingsView(layoutView)

    // TODO: introduce fun onControllerViewEvents(event: SettingsEvent)
    override fun onControllerViewCreated(layoutView: View, controllerView: SettingsView) {
        viewModel.fakeSubscribe { state -> controllerView.render(state) }
        controllerView.subscribeUserInteractionEvents { event ->
            when (event) {
                is SettingsEvent.GoBack -> router.popController(this)
                is SettingsEvent.Licenses -> router.pushWithStandardTransaction(LicensesController())
                is SettingsEvent.AppThemeEditor -> ThemeSelectorDialog.show(router)
                is SettingsEvent.AskClearHistoryConfirmation -> viewModel.setAskConfirmation(event.value)
                is SettingsEvent.DoubleTapExit -> viewModel.setDoubleTapToExit(event.value)
                is SettingsEvent.AutoClearHistory -> withState(viewModel) { state ->
                    val index = SettingsState.getIndexFromDays(state.autoClearHistoryDays)
                    AutoClearHistoryDialog.show(router, this, index)
                }
                is SettingsEvent.GithubPage -> {
                    val url = requireActivity().getString(R.string.settings_github_link).toUri()
                    startActivity(Intent(Intent.ACTION_VIEW).setData(url))
                }
            }
        }
    }

    override fun onAutoClearHistoryCallback(days: Int) {
        viewModel.setAutoClearHistoryDays(days)
    }
}