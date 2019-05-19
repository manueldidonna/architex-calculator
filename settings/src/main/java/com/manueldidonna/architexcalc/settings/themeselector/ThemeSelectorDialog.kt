package com.manueldidonna.architexcalc.settings.themeselector

import android.os.Bundle
import android.view.View
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.bluelinelabs.conductor.Router
import com.manueldidonna.architexcalc.core.ThemeManager
import com.manueldidonna.architexcalc.core.base.BaseDialogController
import com.manueldidonna.architexcalc.settings.R
import org.koin.core.inject

class ThemeSelectorDialog : BaseDialogController() {

    private val themeManager by inject<ThemeManager>()

    private val initialIndex: Int
        get() = when {
            themeManager.isDefaultEnabled() -> 2
            themeManager.isDarkEnabled() -> 1
            else -> 0
        }

    private var themeUpdated = false

    override fun onCreateDialog(savedViewState: Bundle?) = materialDialog {
        listItemsSingleChoice(
            R.array.settings_theme_mode_values,
            initialSelection = initialIndex,
            waitForPositiveButton = false
        ) { _, index: Int, _ ->
            themeUpdated = true
            when (index) {
                0 -> themeManager.enableDarkMode(false)
                1 -> themeManager.enableDarkMode(true)
                else -> themeManager.enableDefaultMode()
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