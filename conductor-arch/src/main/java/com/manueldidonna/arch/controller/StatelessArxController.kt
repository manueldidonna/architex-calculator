package com.manueldidonna.arch.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class StatelessArxController : AbstractArxController {

    protected constructor() : super()
    protected constructor(args: Bundle) : super(args)

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedViewState: Bundle?): View {
        return inflateView(inflater, container).also { view -> onViewCreated(view) }
    }

    abstract fun inflateView(inflater: LayoutInflater, container: ViewGroup): View

    open fun onViewCreated(view: View) {}
}