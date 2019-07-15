package com.manueldidonna.arch.controller

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RestoreViewOnCreateController
import timber.log.Timber
import java.util.*

abstract class AbstractArxController : RestoreViewOnCreateController {

    protected constructor() : super()
    protected constructor(args: Bundle) : super(args)

    var controllerId: String = "${this::class.java.simpleName}@${UUID.randomUUID()}"
        private set

    init {
        addLifecycleListener(object : LifecycleListener() {

            override fun preCreateView(controller: Controller) {
                Timber.d("Create view for $controllerId")
            }

            override fun preAttach(controller: Controller, view: View) {
                Timber.d("Attach view for $controllerId")
            }

            override fun preDetach(controller: Controller, view: View) {
                Timber.d("Detach view for $controllerId")
            }

            override fun preDestroyView(controller: Controller, view: View) {
                Timber.d("Destroy view for $controllerId")
            }

            override fun preContextAvailable(controller: Controller) {
                Timber.d("Context available for $controllerId")
            }

            override fun preContextUnavailable(controller: Controller, context: Context) {
                Timber.d("Context unavailable for $controllerId")
            }

            override fun preDestroy(controller: Controller) {
                Timber.d("Destroy for $controllerId")
            }
        })
    }

    @CallSuper
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.d("Save state for $controllerId")
        outState.putString(PERSISTED_OWNER_ID_KEY, controllerId)
    }

    @CallSuper
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getString(PERSISTED_OWNER_ID_KEY)?.let { controllerId = it }
        Timber.d("Restore state for $controllerId")
    }

    /**
     * Override [handleBack] and return true if you want to handle back
     */
    override fun handleBack(): Boolean {
        return false
    }

    companion object {
        private const val PERSISTED_OWNER_ID_KEY = "arx:persisted_owner_id"
    }
}