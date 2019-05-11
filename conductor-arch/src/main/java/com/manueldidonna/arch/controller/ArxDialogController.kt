package com.manueldidonna.arch.controller

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StyleRes
import com.bluelinelabs.conductor.RestoreViewOnCreateController
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.SimpleSwapChangeHandler

abstract class ArxDialogController : RestoreViewOnCreateController {

    protected constructor() : super()
    protected constructor(args: Bundle) : super(args)

    private var dismissed: Boolean = true
    private var dialog: Dialog? = null

    @StyleRes
    open fun getTheme(): Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedViewState: Bundle?): View {
        dialog = onCreateDialog(savedViewState).apply {
            ownerActivity = this@ArxDialogController.activity
            setOnDismissListener {
                dismissDialog()
            }
            savedViewState?.getBundle(SAVED_DIALOG_STATE_TAG)?.let { dialogState ->
                onRestoreInstanceState(dialogState)
            }
        }
        return View(activity)
    }

    override fun onSaveViewState(view: View, outState: Bundle) {
        super.onSaveViewState(view, outState)
        dialog?.onSaveInstanceState()?.let { dialogState ->
            outState.putBundle(SAVED_DIALOG_STATE_TAG, dialogState)
        }
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        dialog?.show()
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
        dialog?.hide()
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        dialog?.apply {
            setOnDismissListener(null)
            dismiss()
        }
        dialog = null
    }

    fun showDialog(router: Router, tag: String? = null) {
        dismissed = false
        RouterTransaction.with(this)
            .pushChangeHandler(SimpleSwapChangeHandler(false))
            .popChangeHandler(SimpleSwapChangeHandler(false))
            .tag(tag)
            .let(router::pushController)
    }

    fun dismissDialog() {
        if (!dismissed) {
            router.popController(this)
            dismissed = true
        }
    }

    protected abstract fun onCreateDialog(savedViewState: Bundle?): Dialog

    companion object {
        private const val SAVED_DIALOG_STATE_TAG = "android:savedDialogState"
    }
}
