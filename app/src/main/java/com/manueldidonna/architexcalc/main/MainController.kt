package com.manueldidonna.architexcalc.main

import android.view.View
import androidx.annotation.StringRes
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.support.RouterPagerAdapter
import com.manueldidonna.arch.controller.requireActivity
import com.manueldidonna.arch.viewmodel.arxViewModel
import com.manueldidonna.architexcalc.R
import com.manueldidonna.architexcalc.core.base.BaseController
import com.manueldidonna.architexcalc.core.extensions.clipboardManager
import com.manueldidonna.architexcalc.core.extensions.saveTextToClipBoard
import com.manueldidonna.architexcalc.core.extensions.setRootController
import com.manueldidonna.architexcalc.core.extensions.shortToast
import com.manueldidonna.architexcalc.main.copypaste.CopyPasteDialog
import com.manueldidonna.architexcalc.main.numkeyboard.NumKeyboardController
import org.koin.core.parameter.parametersOf

class MainController : BaseController<MainView>(), CopyPasteDialog.Listener {

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
                is MainEvent.Settings -> getNavigator<Navigation>().openSettings()
                is MainEvent.History -> getNavigator<Navigation>().openHistory()
                is MainEvent.CopyResult -> saveToClipBoard(event.text, R.string.clipboard_result_copied)
                is MainEvent.CopyPasteExpression -> with(requireActivity()) {
                    val savedClip = clipboardManager().primaryClip?.getItemAt(0)
                    if (savedClip == null || savedClip.text.isEmpty()) {
                        if (event.text.isEmpty())
                            shortToast(R.string.clipboard_empty_expression)
                        else
                            saveToClipBoard(event.text, R.string.clipboard_expression_copied)
                    } else {
                        val savedText = savedClip.text.toString()
                        CopyPasteDialog.show(router, this@MainController, savedText, event.text)
                    }
                }
            }
        }
    }

    override fun saveToClipBoard(text: String, @StringRes toastMessage: Int) {
        with(requireActivity()) {
            saveTextToClipBoard("ARX_CALCULATOR", text)
            shortToast(toastMessage)
        }
    }

    override fun pasteExpression(expression: String, overwriteCurrentExpression: Boolean) {
        viewModel.appendExpression(expression, overwriteCurrentExpression)
    }

    interface Navigation {
        fun openSettings()
        fun openHistory()
    }
}