package com.manueldidonna.architexcalc.core.base

import android.os.Bundle
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.manueldidonna.arch.controller.ArxDialogController
import com.manueldidonna.arch.controller.requireActivity
import com.manueldidonna.architexcalc.core.R
import com.manueldidonna.architexcalc.core.extensions.resolveColor
import org.koin.core.KoinComponent
import org.koin.core.scope.Scope

abstract class BaseDialogController : ArxDialogController, KoinComponent {

    protected constructor() : super()
    protected constructor(args: Bundle) : super(args)

    protected val controllerScope: Scope by lazy { getKoin().createScope(ownerId) }

    protected inline fun materialDialog(constructor: MaterialDialog.() -> Unit): MaterialDialog {
        return MaterialDialog(requireActivity(), BottomSheet(LayoutMode.WRAP_CONTENT)).apply {
            window?.navigationBarColor = requireActivity().resolveColor(R.attr.colorSurface)
            constructor()
        }
    }
}