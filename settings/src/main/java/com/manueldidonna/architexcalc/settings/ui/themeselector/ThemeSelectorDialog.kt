package com.manueldidonna.architexcalc.settings.ui.themeselector

import android.os.Bundle
import android.view.View
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.bluelinelabs.conductor.Router
import com.manueldidonna.arch.controller.requireActivity
import com.manueldidonna.architexcalc.common.ui.materialDialog
import com.manueldidonna.architexcalc.common.ui.theme.ThemeManager
import com.manueldidonna.architexcalc.common.ui.theme.ThemeMode
import com.manueldidonna.architexcalc.core.base.BaseDialogController
import com.manueldidonna.architexcalc.settings.R
import org.koin.core.inject

internal class ThemeSelectorDialog : BaseDialogController() {

    private val themeManager by inject<ThemeManager>()

    private val initialIndex: Int
        get() = when (themeManager.selectedThemeMode) {
            ThemeMode.DEFAULT -> 2
            ThemeMode.DARK -> 1
            else -> 0
        }

    private var themeUpdated = false

    override fun onCreateDialog(savedViewState: Bundle?) = materialDialog(requireActivity()) {
        listItemsSingleChoice(
            R.array.settings_theme_mode_values,
            initialSelection = initialIndex,
            waitForPositiveButton = false
        ) { _, index: Int, _ ->
            themeUpdated = true
            when (index) {
                0 -> themeManager.selectedThemeMode = ThemeMode.LIGHT
                1 -> themeManager.selectedThemeMode = ThemeMode.DARK
                else -> themeManager.selectedThemeMode = ThemeMode.DEFAULT
            }
        }
    }

    override fun onAttach(view: View) {
        // avoid dialog appears when theme manager recreates activity
        if (themeUpdated)
            dismissDialog()
        else super.onAttach(view)
    }

    companion object {
        fun show(router: Router) {
            ThemeSelectorDialog().showDialog(router)
        }
    }
}