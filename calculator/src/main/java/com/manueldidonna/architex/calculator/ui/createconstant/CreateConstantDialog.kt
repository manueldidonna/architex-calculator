package com.manueldidonna.architex.calculator.ui.createconstant

import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import com.bluelinelabs.conductor.Router
import com.google.android.material.textfield.TextInputEditText
import com.manueldidonna.arch.controller.requireActivity
import com.manueldidonna.architex.calculator.R
import com.manueldidonna.architex.calculator.domain.InsertConstantItemInteractor
import com.manueldidonna.architexcalc.common.ui.extensions.find
import com.manueldidonna.architexcalc.common.ui.materialDialog
import com.manueldidonna.architexcalc.core.base.BaseDialogController
import com.manueldidonna.architexcalc.core.launchInteractor
import kotlinx.coroutines.GlobalScope
import org.koin.core.inject

internal class CreateConstantDialog(args: Bundle) : BaseDialogController(args) {

    private val insertConstant: InsertConstantItemInteractor by inject()

    private val savedValueText = args.getString(VALUE_TEXT_KEY, "")
    override fun onCreateDialog(savedViewState: Bundle?): Dialog {
        return materialDialog(requireActivity(), R.layout.dialog_create_constant) { cView ->
            val nameText = cView.find<TextInputEditText>(R.id.constant_name_text)
            val valueText = cView.find<TextInputEditText>(R.id.constant_value_text) {
                if (savedValueText.isNotEmpty())
                    setText(savedValueText)
            }
            positiveButton(text = "Create") {
                GlobalScope.launchInteractor(
                    interactor = insertConstant,
                    param = InsertConstantItemInteractor.Params(
                        name = nameText.text.toString(),
                        value = valueText.text.toString()
                    )
                )
            }
            negativeButton(text = "Cancel")
        }
    }

    companion object {
        private const val VALUE_TEXT_KEY = "create_constant_dialog:saved_value_text"
        fun show(router: Router, initialValue: String = "") {
            CreateConstantDialog(bundleOf(VALUE_TEXT_KEY to initialValue)).showDialog(router)
        }
    }
}