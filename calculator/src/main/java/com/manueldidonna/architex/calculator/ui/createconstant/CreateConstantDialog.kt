package com.manueldidonna.architex.calculator.ui.createconstant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.setActionButtonEnabled
import com.bluelinelabs.conductor.Router
import com.google.android.material.textfield.TextInputEditText
import com.manueldidonna.arch.controller.requireActivity
import com.manueldidonna.architex.calculator.R
import com.manueldidonna.architex.calculator.domain.InsertConstantItemInteractor
import com.manueldidonna.architexcalc.common.ui.extensions.find
import com.manueldidonna.architexcalc.common.ui.materialDialog
import com.manueldidonna.architexcalc.core.base.BaseDialogController
import com.manueldidonna.architexcalc.core.launchInteractor
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.onEach
import org.koin.core.inject

internal class CreateConstantDialog : BaseDialogController() {

    private val insertConstant: InsertConstantItemInteractor by inject()

    // TODO: add controllerCoroutineScope & controllerViewCoroutineScope to the base controller class
    private lateinit var controllerViewCoroutineScope: CoroutineScope

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedViewState: Bundle?): View {
        controllerViewCoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
        return super.onCreateView(inflater, container, savedViewState)
    }

    //  TODO: use string resources for hardcoded texts -> buttons, title, input errors & input hints
    override fun onCreateDialog(savedViewState: Bundle?) = materialDialog(
        context = requireActivity(),
        customViewRes = R.layout.dialog_create_constant
    ) { cView ->
        // input texts
        val nameText = cView.find<TextInputEditText>(R.id.constant_name_text)
        val valueText = cView.find<TextInputEditText>(R.id.constant_value_text)

        negativeButton(text = "Cancel")
        positiveButton(text = "Create") {
            GlobalScope.launchInteractor(
                interactor = insertConstant,
                param = InsertConstantItemInteractor.Params(
                    name = nameText.text.toString(),
                    value = valueText.text.toString()
                )
            )
        }

        controllerViewCoroutineScope.validateInputs(
            firstInputFlow = nameText.validateName().onEach { isValid ->
                if (!isValid) nameText.error = "Invalid name"
            },
            secondInputFlow = valueText.validateValue().onEach { isValid ->
                if (!isValid) valueText.error = "Invalid value"
            },
            doOnValidation = { isValid ->
                setActionButtonEnabled(WhichButton.POSITIVE, isValid)
            }
        )
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        controllerViewCoroutineScope.cancel()
    }

    companion object {
        fun show(router: Router) = CreateConstantDialog().showDialog(router)
    }
}