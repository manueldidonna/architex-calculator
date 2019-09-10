package com.manueldidonna.arch.controller

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.StyleRes
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.SimpleSwapChangeHandler

abstract class DialogArxController : AbstractArxController {

    protected constructor() : super()
    protected constructor(args: Bundle) : super(args)

    private var dismissed: Boolean = true
    private var dialog: Dialog? = null

    @StyleRes
    open fun getTheme(): Int = 0

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedViewState: Bundle?): View {
        dialog = onCreateDialog(savedViewState).apply {
            setOnDismissListener {
                dismissDialog()
            }
            savedViewState?.getBundle(SAVED_DIALOG_STATE_TAG)?.let { dialogState ->
                onRestoreInstanceState(dialogState)
            }
        }
        return View(activity)
    }

    @CallSuper
    override fun onSaveViewState(view: View, outState: Bundle) {
        super.onSaveViewState(view, outState)
        dialog?.onSaveInstanceState()?.let { dialogState ->
            outState.putBundle(SAVED_DIALOG_STATE_TAG, dialogState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(DISMISSED_STATE_TAG, dismissed)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        dismissed = savedInstanceState.getBoolean(DISMISSED_STATE_TAG)
    }

    @CallSuper
    override fun onAttach(view: View) {
        super.onAttach(view)
        dialog?.show()
    }

    @CallSuper
    override fun onDetach(view: View) {
        super.onDetach(view)
        dialog?.hide()
    }

    @CallSuper
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
        private const val SAVED_DIALOG_STATE_TAG = "arx_dialog:savedViewState"
        private const val DISMISSED_STATE_TAG = "arx_dialog:dismissed"
    }
}