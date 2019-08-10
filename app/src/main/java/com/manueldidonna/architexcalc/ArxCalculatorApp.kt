package com.manueldidonna.architexcalc

import android.app.Application
import androidx.work.Configuration
import com.manueldidonna.architex.calculator.calculatorKoinModule
import com.manueldidonna.architexcalc.common.ui.ThemeManager
import com.manueldidonna.architexcalc.common.ui.commonUIKoinModule
import com.manueldidonna.architexcalc.core.coreKoinModule
import com.manueldidonna.architexcalc.database.databaseKoinModule
import com.manueldidonna.architexcalc.history.historyKoinModule
import com.manueldidonna.architexcalc.settings.settingsKoinModule
import com.manueldidonna.architexcalc.util.TimberKoinLogger
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class ArxCalculatorApp : Application(), Configuration.Provider {
    override fun onCreate() {
        super.onCreate()
        // Logger
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())

        // Koin
        startKoin {
            logger(TimberKoinLogger())
            androidContext(this@ArxCalculatorApp)
            modules(
                listOf(
                    coreKoinModule,
                    commonUIKoinModule,
                    databaseKoinModule,
                    calculatorKoinModule,
                    settingsKoinModule,
                    historyKoinModule
                )
            )
        }

        // ThemeManager
        get<ThemeManager>().init()
    }

    override fun getWorkManagerConfiguration() = Configuration.Builder()
        .setMinimumLoggingLevel(android.util.Log.INFO)
        .build()
}