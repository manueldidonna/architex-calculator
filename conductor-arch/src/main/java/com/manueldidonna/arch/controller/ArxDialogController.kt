package com.manueldidonna.arch.controller

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.StyleRes
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RestoreViewOnCreateController
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.SimpleSwapChangeHandler
import com.manueldidonna.arch.viewmodel.ArxViewModelOwner
import com.manueldidonna.arch.viewmodel.ArxViewModelStore
import timber.log.Timber
import java.util.*

abstract class ArxDialogController : RestoreViewOnCreateController, ArxViewModelOwner {

    protected constructor() : super()
    protected constructor(args: Bundle) : super(args)

    override val viewModelStore = ArxViewModelStore()

    final override val ownerId: String get() = arxPersistedOwnerId

    private var arxPersistedOwnerId: String = "${this::class.java.simpleName}@${UUID.randomUUID()}"

    private var dismissed: Boolean = true
    private var dialog: Dialog? = null

    @StyleRes
    open fun getTheme(): Int = 0

    init {
        addLifecycleListener(object : LifecycleListener() {
            override fun preCreateView(controller: Controller) {
                Timber.d("Create view for $ownerId")
            }

            override fun preContextAvailable(controller: Controller) {
                Timber.d("Context available for $ownerId")
            }

            override fun preContextUnavailable(controller: Controller, context: Context) {
                Timber.d("Context unavailable for $ownerId")
            }

            override fun preDestroy(controller: Controller) {
                Timber.d("Destroy for $ownerId")
                viewModelStore.clear()
            }
        })
    }

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

    @CallSuper
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.d("Save state for $ownerId")
        outState.putString(PERSISTED_OWNER_ID_KEY, ownerId)
    }

    @CallSuper
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getString(PERSISTED_OWNER_ID_KEY)?.let { arxPersistedOwnerId = it }
        Timber.d("Restore state for $ownerId")
    }

    @CallSuper
    override fun onAttach(view: View) {
        super.onAttach(view)
        Timber.d("Attach view for $ownerId")
        dialog?.show()
    }

    @CallSuper
    override fun onDetach(view: View) {
        super.onDetach(view)
        Timber.d("Detach view for $ownerId")
        dialog?.hide()
    }

    @CallSuper
    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        Timber.d("Destroy view for $ownerId")
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
        private const val PERSISTED_OWNER_ID_KEY = "arx:persisted_owner_id"
    }
}
