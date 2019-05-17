package com.manueldidonna.architexcalc.core.base

import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.manueldidonna.arch.controller.ArxDialogController
import com.manueldidonna.arch.controller.requireActivity

abstract class BaseDialogController : ArxDialogController {
    protected constructor() : super()
    protected constructor(args: Bundle) : super(args)

    protected inline fun materialDialog(constructor: MaterialDialog.() -> Unit): MaterialDialog {
        return MaterialDialog(requireActivity(), BottomSheet()).apply { constructor() }
    }
}