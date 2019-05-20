package com.manueldidonna.arch.controller

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RestoreViewOnCreateController
import com.manueldidonna.arch.viewmodel.ArxViewModelOwner
import com.manueldidonna.arch.viewmodel.ArxViewModelStore
import timber.log.Timber
import java.util.*

abstract class ArxController : RestoreViewOnCreateController(), ArxViewModelOwner {

    final override val viewModelStore = ArxViewModelStore()

    final override val ownerId: String get() = arxPersistedOwnerId

    private var arxPersistedOwnerId: String = "${this::class.java.simpleName}@${UUID.randomUUID()}"

    init {
        addLifecycleListener(object : LifecycleListener() {
            override fun postCreateView(controller: Controller, view: View) {
                onViewCreated(view)
            }

            override fun preCreateView(controller: Controller) {
                Timber.d("Create view for $ownerId")
            }

            override fun preAttach(controller: Controller, view: View) {
                Timber.d("Attach view for $ownerId")
            }

            override fun preDetach(controller: Controller, view: View) {
                Timber.d("Detach view for $ownerId")
            }

            override fun preDestroyView(controller: Controller, view: View) {
                Timber.d("Destroy view for $ownerId")
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

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedViewState: Bundle?): View {
        return inflateView(inflater, container)
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

    /**
     * Override [handleBack] and return true if you want to handle back
     */
    override fun handleBack(): Boolean {
        return false
    }

    abstract fun inflateView(inflater: LayoutInflater, container: ViewGroup): View

    open fun onViewCreated(view: View) {}

    protected val parentViewModelOwner: ArxViewModelOwner? get() = parentController as? ArxViewModelOwner

    protected fun requireParentViewModelOwner(): ArxViewModelOwner {
        return requireParentController() as? ArxViewModelOwner
            ?: throw IllegalStateException("Parent Controller does not implement ArxViewModelOwner")
    }

    companion object {
        private const val PERSISTED_OWNER_ID_KEY = "arx:persisted_owner_id"
    }
}