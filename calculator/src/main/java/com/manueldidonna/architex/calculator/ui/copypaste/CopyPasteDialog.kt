package com.manueldidonna.architex.calculator.ui.copypaste

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.os.bundleOf
import com.afollestad.materialdialogs.MaterialDialog
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.manueldidonna.arch.controller.castTargetController
import com.manueldidonna.arch.controller.requireActivity
import com.manueldidonna.arch.controller.setTarget
import com.manueldidonna.architex.calculator.R
import com.manueldidonna.architexcalc.common.ui.materialDialog
import com.manueldidonna.architexcalc.core.base.BaseDialogController
import com.manueldidonna.architexcalc.core.extensions.find

internal class CopyPasteDialog(args: Bundle) : BaseDialogController(args) {

    private var savedClipText: String = getArgs().getString(CLIP_TEXT_KEY, "")
    private var currentExpression: String = getArgs().getString(EXPRESSION_KEY, "")

    override fun onCreateDialog(savedViewState: Bundle?): Dialog {
        return materialDialog(requireActivity(), R.layout.dialog_copy_paste) { cview ->
            if (savedClipText.isEmpty())
                throw IllegalStateException("Empty text saved on clipboard")
            val targetListener = castTargetController<Listener>()
            @SuppressLint("SetTextI18n")
            cview.find<AppCompatTextView>(R.id.dialog_copied_expression).text = "\"$savedClipText\""
            cview.find<View>(R.id.dialog_action_copy_expression)
                .setGoneWithEmptyExpression()
                .dismissOnClickListener(this) {
                    targetListener.onSaveToClipboardCallback(currentExpression, R.string.clipboard_expression_copied)
                }
            cview.find<View>(R.id.dialog_action_overwrite_expression)
                .setGoneWithEmptyExpression()
                .dismissOnClickListener(this) {
                    targetListener.onPasteExpressionCallback(savedClipText, true)
                }
            cview.find<View>(R.id.dialog_action_append_expression)
                .dismissOnClickListener(this) {
                    targetListener.onPasteExpressionCallback(savedClipText, false)
                }
        }
    }

    private fun View.setGoneWithEmptyExpression(): View = apply {
        if (currentExpression.isEmpty())
            visibility = View.GONE
    }

    private inline fun View.dismissOnClickListener(dialog: MaterialDialog, crossinline body: () -> Unit) {
        setOnClickListener {
            body()
            dialog.dismiss()
        }
    }

    interface Listener {
        fun onPasteExpressionCallback(expression: String, overwriteCurrentExpression: Boolean)
        fun onSaveToClipboardCallback(text: String, @StringRes toastMessage: Int)
    }

    companion object {
        private const val CLIP_TEXT_KEY = "copy_paste_dialog:saved_clip_text"
        private const val EXPRESSION_KEY = "copy_paste_dialog:current_expression"

        fun <T> show(
            router: Router,
            target: T,
            savedClipText: String,
            currentExpression: String
        ) where T : Controller, T : Listener {
            val bundle = bundleOf(CLIP_TEXT_KEY to savedClipText, EXPRESSION_KEY to currentExpression)
            return CopyPasteDialog(bundle).setTarget(target).showDialog(router)
        }
    }
}