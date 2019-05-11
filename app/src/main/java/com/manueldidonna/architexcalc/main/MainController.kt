package com.manueldidonna.architexcalc.main

import android.content.ClipData
import android.view.View
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.support.RouterPagerAdapter
import com.manueldidonna.arch.controller.requireActivity
import com.manueldidonna.arch.viewmodel.arxViewModel
import com.manueldidonna.architexcalc.R
import com.manueldidonna.architexcalc.core.base.BaseController
import com.manueldidonna.architexcalc.core.extensions.clipboardManager
import com.manueldidonna.architexcalc.core.extensions.setRootController
import com.manueldidonna.architexcalc.core.extensions.shortToast
import com.manueldidonna.architexcalc.main.numkeyboard.NumKeyboardController
import org.koin.core.parameter.parametersOf

class MainController : BaseController<MainView>() {

    override val layoutRes: Int = R.layout.controller_main

    private val viewModel by arxViewModel(newState = MainState()) { state ->
        controllerScope.get<MainViewModel> { parametersOf(state) }
    }

    private val pagerAdapter = object : RouterPagerAdapter(this) {
        override fun getCount(): Int = 1

        override fun configureRouter(router: Router, position: Int) {
            if (!router.hasRootController()) {
                val controller = when (position) {
                    0 -> NumKeyboardController()
                    else -> TODO("Create FuncKeyboardController")
                }
                router.setRootController(controller)
            }
        }
    }

    override fun onCreateControllerView(layoutView: View) = MainView(layoutView, pagerAdapter)


    override fun onControllerViewCreated(layoutView: View, controllerView: MainView) {
        viewModel.fakeSubscribe { state -> controllerView.render(state) }
        controllerView.subscribeUserInteractionEvents { event: MainEvent ->
            when (event) {
                is MainEvent.CopyToClipboard -> setValueToClipboard(event.label, event.text)
                is MainEvent.Settings -> getNavigator<Navigation>().openSettings()
                is MainEvent.History -> getNavigator<Navigation>().openHistory()
            }
        }
    }

    private fun setValueToClipboard(label: String, text: String) = with(requireActivity()) {
        clipboardManager().primaryClip = ClipData.newPlainText(label, text)
        shortToast(requireActivity().getString(R.string.clipboard_value_copied))
    }

    fun pasteExpressionFromOutside(expr: String) {
        // TODO: invoked by activity as a callback from history
    }

    interface Navigation {
        fun openSettings()
        fun openHistory()
    }

}