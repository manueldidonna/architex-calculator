package com.manueldidonna.architex.calculator.ui

import android.content.ClipboardManager
import android.view.View
import com.bluelinelabs.conductor.Controller
import com.manueldidonna.arch.controller.requireActivity
import com.manueldidonna.architexcalc.core.extensions.clipboardManager

class ConductorClipboardChangedListener(
    controller: Controller,
    val onPrimaryClipChangedCallback: () -> Unit
) : ClipboardManager.OnPrimaryClipChangedListener {
    init {
        controller.addLifecycleListener(object : Controller.LifecycleListener() {
            override fun postCreateView(controller: Controller, view: View) {
                controller.requireActivity()
                    .clipboardManager()
                    .addPrimaryClipChangedListener(this@ConductorClipboardChangedListener)
            }

            override fun postDestroyView(controller: Controller) {
                controller.requireActivity()
                    .clipboardManager()
                    .removePrimaryClipChangedListener(this@ConductorClipboardChangedListener)
            }
        })
    }

    override fun onPrimaryClipChanged() {
        onPrimaryClipChangedCallback()
    }
}