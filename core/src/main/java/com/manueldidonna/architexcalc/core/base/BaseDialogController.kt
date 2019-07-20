package com.manueldidonna.architexcalc.core.base

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.manueldidonna.arch.controller.DialogArxController
import com.manueldidonna.arch.controller.requireActivity
import com.manueldidonna.architexcalc.core.R
import com.manueldidonna.architexcalc.core.extensions.resolveColor
import org.koin.core.KoinComponent
import org.koin.core.scope.Scope

abstract class BaseDialogController : DialogArxController, KoinComponent {

    protected constructor() : super()
    protected constructor(args: Bundle) : super(args)

    protected val controllerScope: Scope by lazy { getKoin().createScope(controllerId) }

    protected inline fun materialDialog(dialogConstructor: MaterialDialog.() -> Unit): MaterialDialog {
        return MaterialDialog(requireActivity(), BottomSheet(LayoutMode.WRAP_CONTENT)).apply {
            window?.navigationBarColor = requireActivity().resolveColor(R.attr.colorSurface)
            dialogConstructor()
        }
    }

    protected inline fun materialDialog(
        @LayoutRes customViewRes: Int,
        dialogConstructor: MaterialDialog.(View) -> Unit
    ) = materialDialog {
        customView(customViewRes)
        dialogConstructor(getCustomView())
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        controllerScope.close()
    }
}