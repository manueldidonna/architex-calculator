package com.manueldidonna.architexcalc.main.copypaste

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.os.bundleOf
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.manueldidonna.arch.controller.castTargetController
import com.manueldidonna.architexcalc.R
import com.manueldidonna.architexcalc.core.base.BaseDialogController

class CopyPasteDialog(args: Bundle) : BaseDialogController(args) {

    private var savedClipText: String = ""
    private var currentExpression: String = ""

    init {
        savedClipText = getArgs().getString(CLIP_TEXT_KEY, "")
        currentExpression = getArgs().getString(EXPRESSION_KEY, "")
    }

    override fun onCreateDialog(savedViewState: Bundle?) = materialDialog {
        if (savedClipText.isEmpty())
            throw IllegalStateException("Empty text saved on clipboard")
        customView(R.layout.dialog_copy_paste)
        bindCustomView(getCustomView(), this)
    }

    private fun bindCustomView(view: View, dialog: MaterialDialog) = with(view) {
        @SuppressLint("SetTextI18n")
        findViewById<AppCompatTextView>(R.id.dialog_copied_expression).text = "\"$savedClipText\""
        findViewById<View>(R.id.dialog_action_copy_expression)
            .setGoneWithEmptyExpression()
            .dismissClickListener(dialog) {
                castTargetController<Listener>().saveToClipBoard(
                    currentExpression,
                    R.string.clipboard_expression_copied
                )
            }
        findViewById<View>(R.id.dialog_action_overwrite_expression)
            .setGoneWithEmptyExpression()
            .dismissClickListener(dialog) {
                castTargetController<Listener>().pasteExpression(savedClipText, true)
            }

        findViewById<View>(R.id.dialog_action_append_expression).dismissClickListener(dialog) {
            castTargetController<Listener>().pasteExpression(savedClipText, false)
        }
    }

    private fun View.setGoneWithEmptyExpression(): View {
        if (currentExpression.isEmpty())
            visibility == View.GONE
        return this
    }

    private inline fun View.dismissClickListener(dialog: MaterialDialog, crossinline body: () -> Unit) {
        setOnClickListener {
            body()
            dialog.dismiss()
        }
    }

    interface Listener {
        fun pasteExpression(expression: String, overwriteCurrentExpression: Boolean)
        fun saveToClipBoard(text: String, @StringRes toastMessage: Int)
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
            return CopyPasteDialog(bundle).apply {
                targetController = target
            }.showDialog(router)
        }
    }
}