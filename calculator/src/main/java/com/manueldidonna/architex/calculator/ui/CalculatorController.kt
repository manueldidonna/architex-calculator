package com.manueldidonna.architex.calculator.ui

import android.content.ClipDescription
import android.content.ClipboardManager
import android.view.View
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.support.RouterPagerAdapter
import com.manueldidonna.arch.conductorLazy
import com.manueldidonna.arch.controller.castActivity
import com.manueldidonna.arch.controller.requireActivity
import com.manueldidonna.arch.statemanager.withState
import com.manueldidonna.architex.calculator.CalculatorNavigation
import com.manueldidonna.architex.calculator.R
import com.manueldidonna.architex.calculator.ui.copypaste.CopyPasteDialog
import com.manueldidonna.architex.calculator.ui.funckeyboard.FuncKeyboardController
import com.manueldidonna.architex.calculator.ui.numkeyboard.NumKeyboardController
import com.manueldidonna.architexcalc.core.base.BaseStatefulController
import com.manueldidonna.architexcalc.core.extensions.clipboardManager
import com.manueldidonna.architexcalc.core.extensions.saveTextToClipBoard
import com.manueldidonna.architexcalc.core.extensions.setRootController
import com.manueldidonna.architexcalc.core.extensions.shortToast
import org.koin.core.parameter.parametersOf

class CalculatorController : BaseStatefulController<CalculatorStateManager, CalculatorState>(),
    CopyPasteDialog.Listener {

    override val saveStateOnProcessDeath = true

    override val stateManager by conductorLazy {
        controllerScope.get<CalculatorStateManager> { parametersOf(savedState { CalculatorState() }) }
    }

    private val pagerAdapter = object : RouterPagerAdapter(this) {
        override fun getCount(): Int = 2
        override fun configureRouter(router: Router, position: Int) {
            if (!router.hasRootController()) {
                val controller = when (position) {
                    0 -> NumKeyboardController()
                    1 -> FuncKeyboardController()
                    else -> throw IllegalStateException("Pager index out of bounds")
                }
                router.setRootController(controller)
            }
        }
    }

    private val clipboardListener = ClipboardManager.OnPrimaryClipChangedListener {
        stateManager.isClipboardEmpty(!requireActivity().clipboardManager().hasPrimaryClipWithPlainText())
    }

    override val layoutRes: Int = R.layout.controller_calculator

    override fun onViewCreated(view: View) {
        super.onViewCreated(view)
        // add clipboard listener
        val clipboardManager = requireActivity().clipboardManager()
        stateManager.isClipboardEmpty(!clipboardManager.hasPrimaryClipWithPlainText())
        clipboardManager.addPrimaryClipChangedListener(clipboardListener)
        // add views
        addView(CalculatorView(view, pagerAdapter))
            .subscribeToStateChanges()
            .onEvents { ev ->
                when (ev) {
                    is CalculatorEvent.ShowHistory -> castActivity<CalculatorNavigation>().showHistory()
                    is CalculatorEvent.ShowSettings -> castActivity<CalculatorNavigation>().showSettings()
                    is CalculatorEvent.CopyPasteExpression -> showCopyPasteDialog(ev.text)
                    is CalculatorEvent.CopyResult -> onSaveToClipboardCallback(
                        text = ev.text,
                        toastMessage = R.string.clipboard_result_copied
                    )
                }
            }
        addView(ClipboardButtonView(view))
            .subscribeToStateChanges(
                CalculatorState::isClipboardEmpty,
                CalculatorState::expression
            ) { isClipEmpty, expr -> !isClipEmpty && expr.isEmpty() }
            .onEvents { ev ->
                when (ev) {
                    ClipboardButtonEvent.ShowExpressionFromClip -> requireActivity().shortToast(getTextFromClipboard())
                    ClipboardButtonEvent.PasteExpressionFromClip -> onPasteExpressionCallback(
                        expression = getTextFromClipboard(),
                        overwriteCurrentExpression = true
                    )
                }
            }
    }

    private fun ClipboardManager.hasPrimaryClipWithPlainText(): Boolean {
        return hasPrimaryClip() && primaryClipDescription?.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN) == true
    }

    private fun getTextFromClipboard(): String {
        val clip = requireActivity()
            .clipboardManager()
            .primaryClip
            ?.getItemAt(0)
            ?: return ""
        return clip.text.toString()
    }

    private fun showCopyPasteDialog(expression: String) = with(requireActivity()) {
        val savedText = getTextFromClipboard()
        if (savedText.isEmpty()) {
            if (expression.isEmpty())
                shortToast(R.string.clipboard_empty_expression)
            else
                onSaveToClipboardCallback(expression, R.string.clipboard_expression_copied)
        } else {
            CopyPasteDialog.show(router, this@CalculatorController, savedText, expression)
        }
    }

    override fun onSaveToClipboardCallback(text: String, toastMessage: Int) {
        with(requireActivity()) {
            saveTextToClipBoard("ARX_CALCULATOR", text)
            shortToast(toastMessage)
        }
    }

    override fun onPasteExpressionCallback(expression: String, overwriteCurrentExpression: Boolean) {
        stateManager.appendExpression(expression, overwriteCurrentExpression)
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity()
            .clipboardManager()
            .removePrimaryClipChangedListener(clipboardListener)
    }

    override fun handleBack(): Boolean = withState {
        if (it.tapBackToDelete && it.expression.isNotEmpty()) {
            stateManager.deleteLastElement()
            return true
        }
        return false
    }
}