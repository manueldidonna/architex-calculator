package com.manueldidonna.architexcalc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.manueldidonna.arch.conductorHandleBack
import com.manueldidonna.architex.calculator.CalculatorNavigation
import com.manueldidonna.architex.calculator.ui.CalculatorController
import com.manueldidonna.architexcalc.core.extensions.pushWithStandardTransaction
import com.manueldidonna.architexcalc.core.extensions.setRootController
import com.manueldidonna.architexcalc.core.extensions.shortToast
import com.manueldidonna.architexcalc.core.prefs.CorePreferences
import com.manueldidonna.architexcalc.core.prefs.PreferencesManager
import com.manueldidonna.architexcalc.history.ui.HistoryController
import com.manueldidonna.architexcalc.settings.ui.SettingsController
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject


class ArxCalculatorActivity : AppCompatActivity(), CalculatorNavigation {

    private val prefsManager: PreferencesManager by inject()
    private lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        router = Conductor.attachRouter(this, main_container, savedInstanceState)
        if (!router.hasRootController()) {
            router.setRootController(CalculatorController(), CALCULATOR_TAG)
        }
    }

    override fun showHistory() {
        router.pushWithStandardTransaction(HistoryController(), HISTORY_TAG)
    }

    override fun showSettings() {
        router.pushWithStandardTransaction(SettingsController(), SETTINGS_TAG)
    }

    private val doubleTapToExit: Boolean get() = prefsManager.get(CorePreferences.DOUBLE_TAP_TO_EXIT_APP)

    private var backPressedInMillis = 0L

    override fun onBackPressed() = conductorHandleBack(router) {
        if (!doubleTapToExit || backPressedInMillis + DOUBLE_BACK_TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed()
        } else {
            shortToast(R.string.back_button_again_exit)
            backPressedInMillis = System.currentTimeMillis()
        }
    }

    companion object {
        private const val DOUBLE_BACK_TIME_INTERVAL = 2000
        private const val CALCULATOR_TAG = "calculator_controller_tag"
        private const val SETTINGS_TAG = "settings_controller_tag"
        private const val HISTORY_TAG = "history_controller_tag"
    }
}